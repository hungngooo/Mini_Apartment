package com.miniApartment.miniApartment.Repository;

import com.miniApartment.miniApartment.Entity.Tenants;
import com.miniApartment.miniApartment.Entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    TokenEntity getTokenEntitiesByEmail(String email);
}
