package com.miniApartment.miniApartment.Controller;

import com.miniApartment.miniApartment.Entity.Tenants;
import com.miniApartment.miniApartment.Services.TenantService;
import com.miniApartment.miniApartment.dto.ListTenantDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenants")
@CrossOrigin
public class TenantController {

    @Autowired
    private TenantService tenantService;

    @GetMapping("/getAllTenant")
    public ResponseEntity<List<ListTenantDTO>> getAllTenant(){
        return ResponseEntity.ok(tenantService.getAllTenants());
    }
    @PostMapping("/addNewTenant")
    public ResponseEntity<Tenants> addNewTenant(@RequestBody Tenants tenants){
        return ResponseEntity.ok(tenantService.addTenant(tenants));
    }
}
