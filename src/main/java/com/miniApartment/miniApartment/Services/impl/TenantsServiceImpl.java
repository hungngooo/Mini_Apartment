package com.miniApartment.miniApartment.Services.impl;

import com.miniApartment.miniApartment.Entity.Tenants;
import com.miniApartment.miniApartment.Repository.TenantRepository;
import com.miniApartment.miniApartment.Services.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TenantsServiceImpl implements TenantService {
    @Autowired
    private TenantRepository tenantRepository;
    public Page<Tenants> getAllTenants(Integer pageNo, Integer pageSize, String keySearch) throws Exception {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Tenants> pageResult;
        if(keySearch == null || keySearch.equals("")) {
            pageResult = tenantRepository.findAll(paging);
        } else {
            pageResult = tenantRepository.searchTenantsByFirstNameAndLastName(keySearch,paging);
        }
        if (pageNo > pageResult.getTotalPages()) {
            throw new Exception();
        }
        return pageResult;
    }
    public Tenants addTenant(Tenants tenants){
        return tenantRepository.save(tenants);
    }
    public int deleteTenant(String email) throws Exception {
        try {
            tenantRepository.deleteUserByEmail(email);
            tenantRepository.deleteTenantsByEmail(email);
            return 1;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public Page<Tenants> getTenantByRoomId(Integer pageNo, Integer pageSize,int roomId){
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return tenantRepository.getTenantsByRoomId(roomId,paging);
    }
    public Tenants getTenantByEmail(String email){
        return  tenantRepository.getTenantByEmail(email);
    }
    public void updateTenant(List<Tenants> tenantsList){
        for(Tenants tenant: tenantsList){
            tenantRepository.updatetenants(tenant.getId(),tenant.getRoomId(),tenant.getGender(),tenant.getDateOfBirth()
                    ,tenant.getContact(),tenant.getCitizenId(),tenant.getEmail(),tenant.getCareer(),tenant.getLicensePlate()
                    ,tenant.getVehicleType(),tenant.getVehicleColor(),tenant.getResidenceStatus());
        }
    }
//    public Page<Tenants> searchTenantByName(String keySearch){
//        Pageable paging = PageRequest.of(0, 1);
//        return tenantRepository.searchTenantsByFirstNameAndLastName(keySearch,paging);
//    }
}
