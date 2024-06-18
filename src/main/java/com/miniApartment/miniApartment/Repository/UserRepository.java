package com.miniApartment.miniApartment.Repository;

import com.miniApartment.miniApartment.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


public interface UserRepository extends JpaRepository<User, String> {
    @Query("Select u from User u where u.email = :email")
    User findByEmail(String email);

    Boolean existsByEmail(String email);
}
