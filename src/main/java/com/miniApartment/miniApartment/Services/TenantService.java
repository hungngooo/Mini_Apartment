package com.miniApartment.miniApartment.Services;

import com.miniApartment.miniApartment.Entity.Tenants;
import com.miniApartment.miniApartment.Repository.TenantRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class TenantService {
    @Autowired
    private TenantRepository tenantRepository;
    @Getter
    private int totalPage;
    public Page<Tenants> getAllTenants(Integer pageNo, Integer pageSize) throws Exception {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Tenants> pageResult = tenantRepository.findAll(paging);
        totalPage = pageResult.getTotalPages();
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
