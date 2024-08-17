package com.miniApartment.miniApartment.Services;

import com.miniApartment.miniApartment.Entity.Tenants;
import com.miniApartment.miniApartment.dto.TenantDTO;
import com.miniApartment.miniApartment.dto.TenantDetailDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


import java.util.List;

public interface TenantService {
     List<Tenants> findAllTenantsByRoomId(int roomId);
     Page<Tenants> getAllTenants(Integer pageNo, Integer pageSize, String keySearch) throws Exception ;
     Tenants addTenant(Tenants tenants);
     int deleteTenant(String email) throws Exception;
     Page<TenantDetailDTO> getTenantByRoomId(Integer pageNo, Integer pageSize, int roomId);
     Tenants getTenantByEmail(String email);
     boolean updateTenant(List<Tenants> tenantsList);
}
