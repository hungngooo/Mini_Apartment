package com.miniApartment.miniApartment.Repository;

import com.miniApartment.miniApartment.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);
    @Query("select u from User u where u.email = ?1")
    User findByEmail1(String email);

    Boolean existsByEmail(String email);
}
