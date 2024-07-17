package com.miniApartment.miniApartment.Controller;

import com.miniApartment.miniApartment.Entity.User;
import com.miniApartment.miniApartment.Response.EHttpStatus;
import com.miniApartment.miniApartment.Response.Response;
import com.miniApartment.miniApartment.Services.UserService;
import com.miniApartment.miniApartment.dto.ChangePasswordDTO;
import com.miniApartment.miniApartment.dto.UserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
//    @GetMapping("/getAllUser")
//    public ResponseEntity<List<User>> getAllUser(){
//        return ResponseEntity.ok(userService.getAllUsers());
//    }
    @GetMapping("/getUserById/{id}")
    public Response<UserInfoDTO> getUserById(@PathVariable("id") String id){
        return new Response<>(EHttpStatus.OK,userService.getUserById(id));
    }
    @GetMapping("/getUserByEmail")
    public Response<UserInfoDTO> getUserByEmail(@RequestParam String email){
        return new Response<>(EHttpStatus.OK,userService.getUserByEmail(email));
    }
    @PostMapping("/editProfile")
    public Response<?> editProfile(@RequestBody UserInfoDTO user){
        userService.updateUser(user);
        return new Response<>(EHttpStatus.OK,"update succsess");
    }
//    @PostMapping("/changePassword")
//    public String changePassword(@RequestBody ChangePasswordDTO passwordDTO) {
//        try {
//            if (userService.checkCurrentPass(passwordDTO.getEmail(), passwordDTO.getCurrentPassword())) {
//                User user = userService.getUserByEmail(passwordDTO.getEmail());
//                user.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
//                userService.updateUser(user);
//                return "Change pass successfull";
//            } else {
//                return "Invalid pass or email";
//            }
//        } catch (Exception e){
//            return e.getMessage();
//        }
//    }
}
