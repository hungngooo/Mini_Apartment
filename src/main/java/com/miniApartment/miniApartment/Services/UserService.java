package com.miniApartment.miniApartment.Services;

import com.miniApartment.miniApartment.Entity.Tenants;
import com.miniApartment.miniApartment.Entity.User;
import com.miniApartment.miniApartment.Repository.UserRepository;
import com.miniApartment.miniApartment.dto.ChangePasswordDTO;
import com.miniApartment.miniApartment.dto.UserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.List;

@Service
public interface UserService {
//    UserInfoDTO getUserById(String id);
    UserInfoDTO getUserByEmail(String email);
    User addUser(User user);
    String updateUser(UserInfoDTO userInfoDTO) ;
    boolean checkCurrentPass(String email, String currentPass);
    boolean changePassword(ChangePasswordDTO passwordDTO);
}
