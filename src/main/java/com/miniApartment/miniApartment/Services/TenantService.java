package com.miniApartment.miniApartment.Services;

import com.miniApartment.miniApartment.Entity.Tenants;
import com.miniApartment.miniApartment.Repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantService {
    @Autowired
    private TenantRepository tenantRepository;

    public List<Tenants> getAllTenants(){
        return tenantRepository.findAll();
    }
    public Tenants addTenant(Tenants tenants){
        return tenantRepository.save(tenants);
    }
    public int deleteTenant(String email) throws Exception {
        try {
            tenantRepository.deleteById(email);
            return 1;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public List<Tenants> getTenantByRoomId(int roomId){
        return tenantRepository.getTenantsByRoomId(roomId);
    }
    public Tenants getTenantByEmail(String email){
        return  tenantRepository.getTenantByEmail(email);
    }
    public void updateTenant(List<Tenants> tenantsList){
        for(Tenants tenant: tenantsList){
            tenantRepository.save(tenant);
        }
    }
}
