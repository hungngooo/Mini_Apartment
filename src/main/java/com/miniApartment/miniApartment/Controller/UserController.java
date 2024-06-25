package com.miniApartment.miniApartment.Controller;

import com.miniApartment.miniApartment.Entity.User;
import com.miniApartment.miniApartment.Services.UserService;
import com.miniApartment.miniApartment.dto.ChangePasswordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/getAllUser")
    public ResponseEntity<List<User>> getAllUser(){
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @GetMapping("/getUserById")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<User> getUserById(@RequestParam String id){
        return ResponseEntity.ok(userService.getUserById(id));
    }
    @GetMapping("/getUserByEmail")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email){
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }
    @PostMapping("/editProfile")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<User> editProfile(@RequestBody User user){
        return ResponseEntity.ok(userService.updateUser(user));
    }
    @PostMapping("/changePassword")
    public String changePassword(@RequestBody ChangePasswordDTO passwordDTO) {
        try {
            if (userService.checkCurrentPass(passwordDTO.getEmail(), passwordDTO.getCurrentPassword())) {
                User user = userService.getUserByEmail(passwordDTO.getEmail());
                user.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
                userService.updateUser(user);
                return "Change pass successfull";
            } else {
                return "Invalid pass or email";
            }
        } catch (Exception e){
            return e.getMessage();
        }
    }
}
