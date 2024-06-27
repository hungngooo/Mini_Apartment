package com.miniApartment.miniApartment.Repository;

import com.miniApartment.miniApartment.Entity.Tenants;
import com.miniApartment.miniApartment.dto.ListTenantDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TenantRepository extends JpaRepository<Tenants, String> {
    @Query("select new com.miniApartment.miniApartment.dto.ListTenantDTO(t.email,u.userId" +
            ", t.roomId,t.career,t.licensePlate,t.vehicleType,t.vehicleColor" +
            ",t.representative,t.residenceStatus,t.contractId,u.firstName" +
            ",u.lastName,u.gender,u.dateOfBirth,u.roleId)" +
            "from Tenants t join User u on t.email = u.email where u.roleId = 1")
    List<ListTenantDTO> getAllTenant();
}
