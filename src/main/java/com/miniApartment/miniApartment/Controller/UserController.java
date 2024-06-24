package com.miniApartment.miniApartment.Controller;

import com.miniApartment.miniApartment.Entity.User;
import com.miniApartment.miniApartment.Services.UserService;
import com.miniApartment.miniApartment.dto.ChangePasswordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

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
    public ResponseEntity<ChangePasswordDTO> changePassword(@RequestBody ChangePasswordDTO passwordDTO){
        User user = userService.getUserByEmail(passwordDTO.getEmail());
//        String passInDB = user.getPassword().decode
        return null;
//        return ResponseEntity.ok(userService.changePassword(user));
    }
}
