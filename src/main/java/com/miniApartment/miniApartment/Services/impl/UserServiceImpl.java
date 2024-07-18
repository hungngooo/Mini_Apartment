package com.miniApartment.miniApartment.Services.impl;

import com.miniApartment.miniApartment.Entity.User;
import com.miniApartment.miniApartment.Repository.UserRepository;
import com.miniApartment.miniApartment.Services.UserService;
import com.miniApartment.miniApartment.dto.ChangePasswordDTO;
import com.miniApartment.miniApartment.dto.UserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

//    public UserInfoDTO getUserById(String id){
//        User user = userRepository.findById(id).orElse(null);
//        UserInfoDTO userInfoDTO = new UserInfoDTO(user);
//        return userInfoDTO;
//    }
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
    public void updateUser(UserInfoDTO userInfoDTO) {
        userRepository.updateUserByEmail(
                userInfoDTO.getGender(),
                userInfoDTO.getDateOfBirth(),
                userInfoDTO.getPlaceOfPermanet(),
                userInfoDTO.getContact(),
                userInfoDTO.getCitizenId(),
                userInfoDTO.getEmail());
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
    public boolean changePassword(ChangePasswordDTO passwordDTO){
        if (checkCurrentPass(passwordDTO.getEmail(), passwordDTO.getCurrentPassword())) {
            User user = getUserByEmailReturnEntity(passwordDTO.getEmail());
            user.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
            userRepository.save(user);
            return true;
        }
        return false;
    }
    private User getUserByEmailReturnEntity(String email){
        User user = userRepository.findByEmail(email).orElse(null);
        return user;
    }
}
