package com.miniApartment.miniApartment.Repository;

import com.miniApartment.miniApartment.Entity.Tenants;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TenantRepository extends JpaRepository<Tenants, String> {
    @Query("select t from Tenants t where t.roomId = :roomId")
    List<Tenants> getTenantsByRoomId(@Param("roomId") int roomId);
}
