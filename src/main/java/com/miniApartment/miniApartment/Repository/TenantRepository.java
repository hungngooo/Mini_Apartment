package com.miniApartment.miniApartment.Repository;

import com.miniApartment.miniApartment.Entity.Tenants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface TenantRepository extends JpaRepository<Tenants, Long> {
    @Query(value = "select * from tenants  where roomId = :roomId", nativeQuery = true)
    Page<Tenants> getTenantsByRoomId(@Param("roomId") int roomId, Pageable pageable);

    //    @Query(value = "select t from Tenants t where t.roomId = :roomId")
//    Page<Tenants> getTenantsByRoomIdi(@Param("roomId") int roomId, Pageable pageable);
    @Modifying
    @Transactional
    @Query(value = "delete Tenants t where t.email = :email")
    void deleteTenantsByEmail(String email);
    @Modifying
    @Transactional
    @Query(value = "delete User u where u.email = :email")
    void deleteUserByEmail(String email);

    @Query(value = "select * from tenants  where email = :email", nativeQuery = true)
    Tenants getTenantByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM tenants WHERE firstName LIKE LOWER(CONCAT('%', :keySearch, '%')) OR lastName LIKE LOWER(CONCAT('%', :keySearch, '%'))", nativeQuery = true)
    Page<Tenants> searchTenantsByFirstNameAndLastName(@Param("keySearch") String keySearch, Pageable pageable);
}