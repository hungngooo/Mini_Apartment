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

import java.util.Date;
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

    boolean existsByEmail(String email);

    @Query(value = "select * from tenants  where email = :email", nativeQuery = true)
    Tenants getTenantByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM tenants WHERE firstName LIKE LOWER(CONCAT('%', :keySearch, '%')) OR lastName LIKE LOWER(CONCAT('%', :keySearch, '%'))", nativeQuery = true)
    Page<Tenants> searchTenantsByFirstNameAndLastName(@Param("keySearch") String keySearch, Pageable pageable);
    @Modifying
    @Transactional
    @Query(value = "UPDATE tenants\n" +
            "SET\n" +
            "roomId = :roomId,\n" +
            "gender = :gender,\n" +
            "dateOfBirth = :dateOfBirth,\n" +
            "contact = :contact,\n" +
            "citizenId = :citizenId,\n" +
            "career = :career,\n" +
            "licensePLate = :licensePLate,\n" +
            "vehicleType = :vehicleType,\n" +
            "vehicleColor = :vehicleColor,\n" +
            "residenceStatus = :residenceStatus\n" +
            "WHERE id = :id AND email = :email;", nativeQuery = true)
    void updatetenants(
            @Param("id") long id,
            @Param("roomId") int roomId,
            @Param("gender") boolean gender,
            @Param("dateOfBirth") Date dateOfBirth,
            @Param("contact") String contact,
            @Param("citizenId") String citizenId,
            @Param("email") String email,
            @Param("career") String career,
            @Param("licensePLate") String licensePLate,
            @Param("vehicleType") String vehicleType,
            @Param("vehicleColor") String vehicleColor,
            @Param("residenceStatus") String residenceStatus);


}