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
import java.util.regex.Pattern;

@Service
public class TenantsServiceImpl implements TenantService {
    @Autowired
    private TenantRepository tenantRepository;

    @Override
    public List<Tenants> findAllTenantsByRoomId(int roomId) {
        return tenantRepository.findAllByRoomId(roomId);
    }

    public Page<Tenants> getAllTenants(Integer pageNo, Integer pageSize, String keySearch) throws Exception {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Tenants> pageResult;
        if (keySearch == null || keySearch.equals("")) {
            pageResult = tenantRepository.findAll(paging);
        } else {
            pageResult = tenantRepository.searchTenantsByFirstNameAndLastName(keySearch, paging);
        }
        if (pageNo > pageResult.getTotalPages()) {
            throw new Exception();
        }
        return pageResult;
    }

    public Tenants addTenant(Tenants tenants) {
        if (tenants == null) {
            throw new IllegalArgumentException("Tenant cannot be null or empty");
        }
        if (validateTenant(tenants)) {
            return tenantRepository.save(tenants);

        }
        return null;
    }

    public int deleteTenant(String email) throws Exception {
        try {
            tenantRepository.deleteUserByEmail(email);
            tenantRepository.deleteTenantsByEmail(email);
            return 1;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public Page<Tenants> getTenantByRoomId(Integer pageNo, Integer pageSize, int roomId) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return tenantRepository.getTenantsByRoomId(roomId, paging);
    }

    public Tenants getTenantByEmail(String email) {
        return tenantRepository.getTenantByEmail(email);
    }

    public void updateTenant(List<Tenants> tenantsList) {
        if (tenantsList == null || tenantsList.isEmpty()) {
            throw new IllegalArgumentException("Tenant list cannot be null or empty");
        }
        for (Tenants tenant : tenantsList) {
            if (validateTenant(tenant)) {
                tenantRepository.updatetenants(tenant.getId(), tenant.getRoomId(), tenant.getGender(), tenant.getDateOfBirth()
                        , tenant.getContact(), tenant.getCitizenId(), tenant.getEmail(), tenant.getCareer(), tenant.getLicensePlate()
                        , tenant.getVehicleType(), tenant.getVehicleColor(), tenant.getResidenceStatus());
            }
        }
    }



    private boolean validateTenant(Tenants tenant) {
        if (tenant.getId() == null) {
            System.err.println("Tenant ID cannot be null");
            return false;
        }
        if (tenant.getRoomId() <= 0) {
            System.err.println("Room ID must be greater than zero");
            return false;
        }
//        if (tenant.getGender() == null) {
//            System.err.println("Gender cannot be null");
//            return false;
//        }
        if (tenant.getDateOfBirth() == null) {
            System.err.println("Date of Birth cannot be null");
            return false;
        }
        if (tenant.getContact() == null || tenant.getContact().isEmpty()) {
            System.err.println("Contact cannot be null or empty");
            return false;
        }
        if (tenant.getCitizenId() == null || tenant.getCitizenId().isEmpty()) {
            System.err.println("Citizen ID cannot be null or empty");
            return false;
        } else if (!isValidCitizenId(tenant.getCitizenId())) {
            System.err.println("Invalid Citizen ID format");
            return false;
        }
        if (tenant.getEmail() == null || tenant.getEmail().isEmpty()) {
            System.err.println("Email cannot be null or empty");
            return false;
        } else if (!isValidEmail(tenant.getEmail())) {
            System.err.println("Invalid email format");
            return false;
        }
        if (tenant.getCareer() == null) {
            System.err.println("Career cannot be null");
            return false;
        }
        if (tenant.getLicensePlate() == null || tenant.getLicensePlate().isEmpty()) {
            System.err.println("License Plate cannot be null or empty");
            return false;
        }
        if (tenant.getVehicleType() == null) {
            System.err.println("Vehicle Type cannot be null");
            return false;
        }
        if (tenant.getVehicleColor() == null) {
            System.err.println("Vehicle Color cannot be null");
            return false;
        }
        if (tenant.getResidenceStatus() == null) {
            System.err.println("Residence Status cannot be null");
            return false;
        }
        return true;
    }

//    private boolean isValidContact(String contact) {
//        String regex = "^(\\+84|0)[0-9]{9}$";
//        return Pattern.matches(regex, contact);
//    }

    private boolean isValidCitizenId(String citizenId) {
        String regex = "^[0-9]{12}$";
        return Pattern.matches(regex, citizenId);
    }

    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.matches(regex, email);
    }
}
