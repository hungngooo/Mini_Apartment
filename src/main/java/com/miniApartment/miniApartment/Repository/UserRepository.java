package com.miniApartment.miniApartment.Repository;

import com.miniApartment.miniApartment.Entity.User;
import com.miniApartment.miniApartment.dto.UserInfoDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigInteger;
import java.util.Date;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    @Query("select u from User u where u.email = ?1")
    User findByEmail1(String email);

    Boolean existsByEmail(String email);
    @Query(value = "select password from users where email = :email",nativeQuery = true)
    String getPassByEmail(String email);
    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET " +
            "gender = :gender, " +
            "dateOfBirth = :dateOfBirth, " +
            "placeOfPermanet = :placeOfPermanet, " +
            "contact = :contact, " +
            "citizenId = :citizenId " +
            "WHERE email = :email", nativeQuery = true)
    int updateUserByEmail(
            @Param("gender") boolean gender,
            @Param("dateOfBirth") Date dateOfBirth,
            @Param("placeOfPermanet") String placeOfPermanet,
            @Param("contact") String contact,
            @Param("citizenId") String citizenId,
            @Param("email") String email);
}
