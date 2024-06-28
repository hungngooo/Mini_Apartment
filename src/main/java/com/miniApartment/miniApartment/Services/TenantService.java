package com.miniApartment.miniApartment.Services;

import com.miniApartment.miniApartment.Entity.Tenants;
import com.miniApartment.miniApartment.Repository.TenantRepository;
import com.miniApartment.miniApartment.dto.ListTenantDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantService {
    @Autowired
    private TenantRepository tenantRepository;

    public List<ListTenantDTO> getAllTenants(){
        return tenantRepository.getAllTenant();
    }
    public Tenants addTenant(Tenants tenants){
        return tenantRepository.save(tenants);
    }
}
