package com.miniApartment.miniApartment.Services;

import com.miniApartment.miniApartment.Entity.OtpDetails;
import com.miniApartment.miniApartment.Entity.User;
import com.miniApartment.miniApartment.Repository.UserRepository;
import com.miniApartment.miniApartment.dto.OtpForgetPasswordDTO;
import com.miniApartment.miniApartment.dto.OtpVerificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    private Map<String, OtpDetails> otpStore = new HashMap<>();

    private final PasswordEncoder encoder;

    public UserInfoService() {
        this.encoder = new BCryptPasswordEncoder();
    }

    public void forgetPassword(String email) {
        User user = userRepository.findByEmail1(email);
        if (user == null) {
            throw new RuntimeException("Email not found");
        }
        Random random = new Random();
        int otp = random.nextInt(900000) + 100000;
        LocalDateTime expiredTime = LocalDateTime.now().plusMinutes(5);
        otpStore.put(email, new OtpDetails(String.valueOf(otp), expiredTime));
        emailService.sendMail(email, "Reset password", "Here is OTP " + otp);
    }
    public void resentOtp(String email) {
        otpStore.remove(email);
        Random random = new Random();
        int otp = random.nextInt(900000) + 100000;
        LocalDateTime expiredTime = LocalDateTime.now().plusMinutes(5);
        otpStore.put(email, new OtpDetails(String.valueOf(otp), expiredTime));
        emailService.sendMail(email, "Resent OTP", "Here is OTP " + otp);
    }
    public void loginOtp(String email) {
        Random random = new Random();
        int otp = random.nextInt(900000) + 100000;
        LocalDateTime expiredTime = LocalDateTime.now().plusMinutes(5);
        otpStore.put(email, new OtpDetails(String.valueOf(otp), expiredTime));
        emailService.sendMail(email, "Login Otp", "Here is OTP " + otp);
    }
    public void verifyOtp(OtpForgetPasswordDTO otpForgetPasswordDTO) {
        String email = otpForgetPasswordDTO.getEmail();
        String otp = otpForgetPasswordDTO.getOtp();

        if (!otpStore.containsKey(email)) {
            throw new RuntimeException("OTP not found or expired!");
        }

        OtpDetails otpDetails = otpStore.get(email);
        if (otpDetails.getExpiryTime().isBefore(LocalDateTime.now())) {
            otpStore.remove(email);
            throw new RuntimeException("OTP expired!");
        }

        if (!otpDetails.getOtp().equals(otp)) {
            throw new RuntimeException("Invalid OTP!");
        }

        otpStore.remove(email); // Remove OTP after verification
    }
    public void changeForgetPassword(OtpForgetPasswordDTO otpForgetPasswordDTO) {
        String email = otpForgetPasswordDTO.getEmail();
        String newPassword = otpForgetPasswordDTO.getNewPassword();
        String confirmPassword = otpForgetPasswordDTO.getConfirmPassword();

        if (!newPassword.equals(confirmPassword)) {
            throw new RuntimeException("Passwords do not match!");
        }
        // Update user's password
        User user = userRepository.findByEmail1(email);
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userDetail = userRepository.findByEmail(email);
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + email));
    }

    public String addUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User Added Successfully";
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}


