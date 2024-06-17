package com.miniApartment.miniApartment.Repository;

import com.miniApartment.miniApartment.Entity.role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<role, Integer> {

    Optional<role> findByName(String name);
}
