package com.miniApartment.miniApartment.Repository;

import com.miniApartment.miniApartment.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<Users, String> {

    Optional<Users> findByEmail(String Email);
    Optional<Users> findByPassword(String password);
    Boolean existsByEmail(String email);
    Boolean existsByContact(String contact);
}
