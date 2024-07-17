package com.miniApartment.miniApartment.Controller;

import com.miniApartment.miniApartment.Entity.OtpDetails;
import com.miniApartment.miniApartment.Entity.User;
import com.miniApartment.miniApartment.Response.EHttpStatus;
import com.miniApartment.miniApartment.Response.Response;
import com.miniApartment.miniApartment.Services.EmailService;
import com.miniApartment.miniApartment.Services.JwtService;
import com.miniApartment.miniApartment.Services.UserInfoService;
import com.miniApartment.miniApartment.Services.UserService;
import com.miniApartment.miniApartment.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public AuthController(EmailService emailService) {
        this.emailService = emailService;
    }

    // Generate OTP

    private ConcurrentHashMap<String, OtpDetails> otpStore = new ConcurrentHashMap<>();
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDTO signUpDto) {
        if (userInfoService.existsByEmail(signUpDto.getEmail())) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        if (!signUpDto.getPassword().equals(signUpDto.getRePassword())) {
            return new ResponseEntity<>("Passwords do not match!", HttpStatus.BAD_REQUEST);
        }
        Random random = new Random();
        int otp = random.nextInt(900000) + 100000; // Generate 6-digit OTP
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(5); // Set expiry time to 5 minutes from now

        otpStore.put(signUpDto.getEmail(), new OtpDetails(String.valueOf(otp), expiryTime));

        emailService.sendMail(signUpDto.getEmail(), "Email confirm", "Here is the OTP: " + otp);

        return new ResponseEntity<>("OTP sent to your email. Please verify to complete registration.", HttpStatus.OK);
    }


    @PostMapping("/verifyOtp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpVerificationDTO otpVerificationDto) {
        String email = otpVerificationDto.getEmail();
        String otp = otpVerificationDto.getOtp();

        if (!otpStore.containsKey(email)) {
            return new ResponseEntity<>("OTP not found or expired!", HttpStatus.BAD_REQUEST);
        }

        OtpDetails otpDetails = otpStore.get(email);
        if (otpDetails.getExpiryTime().isBefore(LocalDateTime.now())) {
            otpStore.remove(email);
            return new ResponseEntity<>("OTP expired!", HttpStatus.BAD_REQUEST);
        }

        if (!otpDetails.getOtp().equals(otp)) {
            return new ResponseEntity<>("Invalid OTP!", HttpStatus.BAD_REQUEST);
        }

        otpStore.remove(email); // Remove OTP after verification

        // User is verified, proceed with registration
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setEmail(email);
        user.setFirstName(otpVerificationDto.getFirstName());
        user.setLastName(otpVerificationDto.getLastName());
        user.setPassword(otpVerificationDto.getPassword());
        user.setRoleId(1); // default roleId
        userInfoService.addUser(user);

        // Generate token for the new user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, otpVerificationDto.getPassword())
        );
        String token = jwtService.generateToken(email);

        return ResponseEntity.ok("User sign up successfully. Token: " + token);
    }

    private ConcurrentHashMap<String, OtpDetails> otpResendStore = new ConcurrentHashMap<>();

    @PostMapping("/resendOtpRegister")
    public ResponseEntity<?> resendOtp(@RequestBody SignUpDTO signUpDto) {
        otpStore.remove(signUpDto.getEmail());
        Random random = new Random();
        int otp = random.nextInt(900000) + 100000; // Generate 6-digit OTP
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(5); // Set expiry time to 5 minutes from now
        otpStore.put(signUpDto.getEmail(), new OtpDetails(String.valueOf(otp), expiryTime));
        emailService.sendMail(signUpDto.getEmail(), "Email confirm", "Here is the OTP: " + otp);
        return new ResponseEntity<>("OTP resent to your email. Please verify to complete registration.", HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDTO loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );
        String email = loginDto.getEmail();
        if (!authentication.isAuthenticated()) {
            return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }
        Random random = new Random();
        int otp = random.nextInt(900000) + 100000;
        LocalDateTime expiredTime = LocalDateTime.now().plusMinutes(5);
        otpStore.put(email, new OtpDetails(String.valueOf(otp), expiredTime));
        emailService.sendMail(email, "Login Otp", "Here is OTP " + otp);
        return ResponseEntity.ok("Otp is sent successfully, please check the OTP to verify");
    }

    @PostMapping("/verifyOtpLogin")
    public ResponseEntity<?> verifyOtpLogin(@RequestBody LoginVerifyOtpDTO loginVerifyOtpDTO) {
        String email = loginVerifyOtpDTO.getEmail();
        String otp = loginVerifyOtpDTO.getOtp();

        if (!otpStore.containsKey(email)) {
            return new ResponseEntity<>("OTP not found or expired!", HttpStatus.BAD_REQUEST);
        }

        OtpDetails otpDetails = otpStore.get(email);
        if (otpDetails.getExpiryTime().isBefore(LocalDateTime.now())) {
            otpStore.remove(email);
            return new ResponseEntity<>("OTP expired!", HttpStatus.BAD_REQUEST);
        }

        if (!otpDetails.getOtp().equals(otp)) {
            return new ResponseEntity<>("Invalid OTP!", HttpStatus.BAD_REQUEST);
        }
        otpStore.remove(email);
        String token = jwtService.generateToken(email);
        return ResponseEntity.ok(token);
    }


    @PostMapping("/resendOtpLogin")
    public ResponseEntity<?> resendOtpLogin(@RequestBody LoginDTO LoginDTO) {
        otpStore.remove(LoginDTO.getEmail());
        Random random = new Random();
        int otp = random.nextInt(900000) + 100000;
        LocalDateTime expiredTime = LocalDateTime.now().plusMinutes(5);
        otpStore.put(LoginDTO.getEmail(), new OtpDetails(String.valueOf(otp), expiredTime));
        emailService.sendMail(LoginDTO.getEmail(), "Resent OTP", "Here is OTP " + otp);
            return new ResponseEntity<>("OTP resent to your email. Please verify to login.", HttpStatus.OK);
    }



    @PostMapping("/forgetPassword")
    public ResponseEntity<?> forgetPassword(@RequestBody ForgetPasswordDTO forgetPasswordDTO) {
        try {
            userInfoService.forgetPassword(forgetPasswordDTO.getEmail());
            return ResponseEntity.ok(new Response(EHttpStatus.OK, "OTP sent to your email. Please verify to reset password."));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/verifyOtpForgetPassword")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpForgetPasswordDTO otpForgetPasswordDTO) {
        try {
            userInfoService.verifyOtp(otpForgetPasswordDTO);
            return ResponseEntity.ok(new Response(EHttpStatus.OK, "OTP verified successfully."));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/forgetPasswordChange")
    public ResponseEntity<?> forgetPasswordChange(@RequestBody OtpForgetPasswordDTO otpForgetPasswordDTO) {
        try {
            userInfoService.changeForgetPassword(otpForgetPasswordDTO);
            return ResponseEntity.ok(new Response(EHttpStatus.OK, "Change Password Successfully"));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/resendOtpForgetPassword")
    public ResponseEntity<?> resendOtpForgetPassword(@RequestBody ForgetPasswordDTO forgetPasswordDTO) {
        try {
            userInfoService.resentOtp(forgetPasswordDTO.getEmail());
            return new ResponseEntity<>("OTP resent to your email. Please verify to reset your password.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(loginDTO.getEmail());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }
}
