package com.miniApartment.miniApartment.Services.impl;

import com.miniApartment.miniApartment.Entity.User;
import com.miniApartment.miniApartment.Repository.UserRepository;
import com.miniApartment.miniApartment.Services.UserService;
import com.miniApartment.miniApartment.dto.ChangePasswordDTO;
import com.miniApartment.miniApartment.dto.UserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

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
    public String updateUser(UserInfoDTO userInfoDTO) {
        if(validateUserInfo(userInfoDTO)) {
            userRepository.updateUserByEmail(
                    userInfoDTO.getGender(),
                    userInfoDTO.getDateOfBirth(),
                    userInfoDTO.getPlaceOfPermanet(),
                    userInfoDTO.getContact(),
                    userInfoDTO.getCitizenId(),
                    userInfoDTO.getEmail());
            return "update success";
        }
        return "update fail";
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
    private boolean validateUserInfo(UserInfoDTO userInfoDTO) {
        if (userInfoDTO.getGender() ==0) {
            System.err.println("Gender cannot be null");
            return false;
        }
        if (userInfoDTO.getDateOfBirth() == null) {
            System.err.println("Date of Birth cannot be null");
            return false;
        }
        if (userInfoDTO.getPlaceOfPermanet() == null || userInfoDTO.getPlaceOfPermanet().isEmpty()) {
            System.err.println("Place of Permanent cannot be null or empty");
            return false;
        }
        if (userInfoDTO.getContact() == null || userInfoDTO.getContact().isEmpty()) {
            System.err.println("Contact cannot be null or empty");
            return false;
        }
        if (userInfoDTO.getCitizenId() == null || userInfoDTO.getCitizenId().isEmpty()) {
            System.err.println("Citizen ID cannot be null or empty");
            return false;
        } else if (!isValidCitizenId(userInfoDTO.getCitizenId())) {
            System.err.println("Invalid Citizen ID format");
            return false;
        }
        if (userInfoDTO.getEmail() == null || userInfoDTO.getEmail().isEmpty()) {
            System.err.println("Email cannot be null or empty");
            return false;
        } else if (!isValidEmail(userInfoDTO.getEmail())) {
            System.err.println("Invalid email format");
            return false;
        }

        return true;
    }
    private User getUserByEmailReturnEntity(String email){
        User user = userRepository.findByEmail(email).orElse(null);
        return user;
    }
    private boolean isValidCitizenId(String citizenId) {
        String regex = "^[0-9]{12}$";
        return Pattern.matches(regex, citizenId);
    }

    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.matches(regex, email);
    }
}
