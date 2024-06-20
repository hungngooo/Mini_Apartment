package com.miniApartment.miniApartment.Controller;

import com.miniApartment.miniApartment.Entity.User;
import com.miniApartment.miniApartment.Services.UserService;
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
    @PostMapping("/editProfile")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<User> editProfile(@RequestBody User user){
        return ResponseEntity.ok(userService.updateUser(user));
    }
}
