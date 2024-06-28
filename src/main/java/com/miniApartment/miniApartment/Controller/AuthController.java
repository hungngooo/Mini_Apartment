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
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

        // Generate OTP
        Random random = new Random();
        int otp = random.nextInt(900000) + 100000; // Generate 6-digit OTP
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(2); // Set expiry time to 2 minutes from now

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



    @PostMapping("/login")
public ResponseEntity<?> authenticateUser(@RequestBody LoginDTO loginDto) {
    try {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(loginDto.getEmail());
            return ResponseEntity.ok(token);
        } else {
            return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }
    } catch (AuthenticationException e) {
        return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
    }
}

@GetMapping("/welcome")
public String welcome() {
    return "Welcome this endpoint is not secure";
}

@PostMapping("/addNewUser")
public String addNewUser(@RequestBody User user) {
    return userInfoService.addUser(user);
}

@GetMapping("/user/userProfile")
@PreAuthorize("hasAuthority('ROLE_USER')")
public String userProfile() {
    return "Welcome to User Profile";
}

@GetMapping("/admin/adminProfile")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public String adminProfile() {
    return "Welcome to Admin Profile";
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
    @PostMapping("/forgetPassword")
    public ResponseEntity<?> forgetPassword(@RequestBody ForgetPasswordDTO forgetPasswordDTO) {
        try {
            userInfoService.forgetPassword(forgetPasswordDTO.getEmail());
            return ResponseEntity.ok(new Response(EHttpStatus.OK,"OTP sent to your email. Please verify to reset password."));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/verifyOtpForgetPassword")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpForgetPasswordDTO otpForgetPasswordDTO) {
        try {
            userInfoService.verifyOtp(otpForgetPasswordDTO);
            return ResponseEntity.ok(new Response(EHttpStatus.OK,"Password reset successfully."));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
