package com.miniApartment.miniApartment.Repository;

import com.miniApartment.miniApartment.Entity.User;
import com.miniApartment.miniApartment.dto.UserInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);
    @Query("select u from User u where u.email = ?1")
    User findByEmail1(String email);

    Boolean existsByEmail(String email);
    @Query(value = "select password from users where email = :email",nativeQuery = true)
    String getPassByEmail(String email);
    @Query(value = "UPDATE `users`\n" +
            "SET\n" +
            "`firstName` = :userInfoDTO.firstName,\n" +
            "`lastName` = :userInfoDTO.lastName,\n" +
            "`gender` = :userInfoDTO.gender,\n" +
            "`dateOfBirth` = :userInfoDTO.dateOfBirth,\n" +
            "`placeOfPermanet` = :userInfoDTO.placeOfPermanet,\n" +
            "`contact` = :userInfoDTO.contact,\n" +
            "`citizenId` = :userInfoDTO.citizenId\n" +
            "where `email` = :userInfoDTO.email;\n",nativeQuery = true)
    UserInfoDTO updateUserByEmail(UserInfoDTO userInfoDTO);
}
