package com.miniApartment.miniApartment.Services;

import com.miniApartment.miniApartment.Entity.User;
import com.miniApartment.miniApartment.Repository.UserRepository;
import com.miniApartment.miniApartment.dto.UserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class UserService {
    @Autowired
    private UserRepository  userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
//    public List<UserInfoDTO> getAllUsers(){
//        User user = userRepository.findAll();
//
//        return ;
//    }
    public UserInfoDTO getUserById(String id){
        User user = userRepository.findById(id).orElse(null);
        UserInfoDTO userInfoDTO = new UserInfoDTO(user);
        return userInfoDTO;
    }
    public UserInfoDTO getUserByEmail(String email){
        //tao object de lay du lieu tu repo sql
        User user = userRepository.findByEmail(email).orElse(null);

        //set du lieu vao dto/ entity tra ra cho controller
        UserInfoDTO userInfoDTO = new UserInfoDTO(user);
//        return ascasc;
        return userInfoDTO;
    }
    public User addUser(User user){
        return userRepository.save(user);
    }
    public UserInfoDTO updateUser(UserInfoDTO user) {
        return userRepository.updateUserByEmail(user);
    }
    private String getPassByEmail(String email){
        return userRepository.getPassByEmail(email);
    }
    public boolean checkCurrentPass(String email, String currentPass){
        if(passwordEncoder.matches(currentPass, this.getPassByEmail(email))){
            return true;
        }
        return false;
    }
}
