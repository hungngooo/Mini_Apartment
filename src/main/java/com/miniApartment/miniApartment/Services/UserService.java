package com.miniApartment.miniApartment.Services;

import com.miniApartment.miniApartment.Entity.User;
import com.miniApartment.miniApartment.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class UserService {
    @Autowired
    private UserRepository  userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public User getUserById(String id){
        return userRepository.findById(id).orElse(null);
    }
    public User getUserByEmail(String email){return userRepository.findByEmail(email).orElse(null);}
    public User addUser(User user){
        return userRepository.save(user);
    }
    public User updateUser(User user) {
        return userRepository.save(user);
    }
}
