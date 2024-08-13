package com.miniApartment.miniApartment.Controller;

import com.miniApartment.miniApartment.Entity.OtpDetails;
import com.miniApartment.miniApartment.Entity.Tenants;
import com.miniApartment.miniApartment.Entity.User;
import com.miniApartment.miniApartment.Response.EHttpStatus;
import com.miniApartment.miniApartment.Response.Response;
import com.miniApartment.miniApartment.Services.*;
import com.miniApartment.miniApartment.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
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
    private UserInfoService userDetailsService;

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
    @Autowired
    private TenantService tenantService;

    @PostMapping("/signup")
    public Response<?> registerUser(@RequestBody SignUpDTO signUpDto) {
        Tenants tenant = tenantService.getTenantByEmail(signUpDto.getEmail());
        if (tenant == null) {
            return new Response<>(EHttpStatus.BAD_REQUEST, "Tenant not found. Please create a tenant first.");
        }
        if (!tenant.getFirstName().equals(signUpDto.getFirstName()) || !tenant.getLastName().equals(signUpDto.getLastName()) ||
                !tenant.getEmail().equals(signUpDto.getEmail())) {
            return new Response<>(EHttpStatus.BAD_REQUEST, "Please check the entered information.");
        }
        if (userInfoService.existsByEmail(signUpDto.getEmail())) {
            return new Response<>(EHttpStatus.BAD_REQUEST, "Email is already taken.");
        }
        if (!signUpDto.getPassword().equals(signUpDto.getRePassword())) {
            return new Response<>(EHttpStatus.BAD_REQUEST, "Passwords do not match!");
        }
        Random random = new Random();
        int otp = random.nextInt(900000) + 100000; // Generate 6-digit OTP
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(2); // Set expiry time to 5 minutes from now
        otpStore.put(signUpDto.getEmail(), new OtpDetails(String.valueOf(otp), expiryTime));
        emailService.sendMail(signUpDto.getEmail(), "Email confirm", "Here is the OTP: " + otp);
        return new Response<>(EHttpStatus.OK, "OTP sent to your email. Please verify to complete registration.");
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
    public Response<?> authenticateUser(@RequestBody LoginDTO loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );
        String email = loginDto.getEmail();
        if (!authentication.isAuthenticated()) {
            return new Response<>(EHttpStatus.UNAUTHORIZED,"Invalid email or password");
        }
        Random random = new Random();
        int otp = random.nextInt(900000) + 100000;
        LocalDateTime expiredTime = LocalDateTime.now().plusMinutes(5);
        otpStore.put(email, new OtpDetails(String.valueOf(otp), expiredTime));
        emailService.sendMail(email, "Login Otp", "Here is OTP " + otp);
        return new Response(EHttpStatus.OK,"Otp is sent successfully, please check the OTP to verify");
    }

    @PostMapping("/verifyOtpLogin")
    public Response<?> verifyOtpLogin(@RequestBody LoginVerifyOtpDTO loginVerifyOtpDTO) {
        String email = loginVerifyOtpDTO.getEmail();
        String otp = loginVerifyOtpDTO.getOtp();

        if (!otpStore.containsKey(email)) {
            return new Response<>(EHttpStatus.BAD_REQUEST, "OTP not found or expired!");
        }

        OtpDetails otpDetails = otpStore.get(email);
        if (otpDetails.getExpiryTime().isBefore(LocalDateTime.now())) {
            otpStore.remove(email);
            return new Response<>(EHttpStatus.BAD_REQUEST, "OTP expired!");
        }

        if (!otpDetails.getOtp().equals(otp)) {
            return new Response<>(EHttpStatus.BAD_REQUEST, "Invalid OTP!");
        }
        otpStore.remove(email);
        String token = jwtService.generateToken(email);
        String refreshToken = jwtService.generateNewRefrershToken(email);
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setAccessToken(token);
        tokenDTO.setRefreshToken(refreshToken);
        return new Response<>(EHttpStatus.OK, tokenDTO);
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

    @GetMapping("/checkToken")
    public Response<?> checkToken(@RequestHeader("Authorization") String authHeader) {
        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }
        switch (jwtService.checkTokenExprired(token)){
            case 0: return new Response<>(EHttpStatus.OK, "ok");
            case 1: return new Response<>(EHttpStatus.UNAUTHORIZED, "expired token");
            case 2:return new Response<>(EHttpStatus.INVALID_INFORMATION, "invalid token");
        }
        return null;
    }
    @PostMapping("/refreshToken")
    public Response<?> getAccesstokenByRefreshtoken(@RequestBody Map<String, String> body){
        String token = body.get("token");
        switch (jwtService.validateRefreshToken(token)){
            case 0: return  new Response<>(EHttpStatus.OK,jwtService.generateTokenByRefreshtoken(token));
            case 1: return new Response<>(EHttpStatus.UNAUTHORIZED, "expired token");
            case 2:return new Response<>(EHttpStatus.INVALID_INFORMATION, "invalid token");
        }
        return null;
    }
    @GetMapping("/deleteToken")
    public Response<?> deleteTokenInDB(@RequestHeader("Authorization") String authHeader){
        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }
        jwtService.deleteToken(token);
        return new Response<>(EHttpStatus.OK,"ok");
    }
}
