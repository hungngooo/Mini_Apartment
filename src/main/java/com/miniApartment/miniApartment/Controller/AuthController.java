package com.miniApartment.miniApartment.Controller;

import com.miniApartment.miniApartment.Entity.User;
import com.miniApartment.miniApartment.Services.JwtService;
import com.miniApartment.miniApartment.Services.UserInfoService;
import com.miniApartment.miniApartment.dto.LoginDTO;
import com.miniApartment.miniApartment.dto.SignUpDTO;
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

import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDTO signUpDto) {
        if (userInfoService.existsByEmail(signUpDto.getEmail())) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        if (!signUpDto.getPassword().equals(signUpDto.getRePassword())) {
            return new ResponseEntity<>("Passwords do not match!", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setEmail(signUpDto.getEmail());
        user.setFirstName(signUpDto.getFirstName());
        user.setLastName(signUpDto.getLastName());
        user.setPassword(signUpDto.getPassword());
        user.setRoleId(1); // default roleId

        userInfoService.addUser(user);

        // Generate token for the new user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signUpDto.getEmail(), signUpDto.getPassword())
        );
        String token = jwtService.generateToken(signUpDto.getEmail());

        return ResponseEntity.ok("User sign up successfully");
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
}
