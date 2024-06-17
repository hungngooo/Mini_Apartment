package com.miniApartment.miniApartment.Repository;

import com.miniApartment.miniApartment.Entity.users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<users, String> {

    Optional<users> findByEmail(String Email);
    Optional<users> findByPassword(String password);
    Boolean existsByEmail(String email);
    Boolean existsByContact(String contact);
}
