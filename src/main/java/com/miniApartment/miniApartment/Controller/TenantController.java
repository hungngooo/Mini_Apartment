package com.miniApartment.miniApartment.Controller;

import com.miniApartment.miniApartment.Entity.Tenants;
import com.miniApartment.miniApartment.Services.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<Page<Tenants>> getAllTenant(@RequestParam(defaultValue = "0") Integer pageNo,
                                                      @RequestParam(defaultValue = "10") Integer pageSize) throws Exception {
        return ResponseEntity.ok(tenantService.getAllTenants(pageNo,pageSize));
    }
    @PostMapping("/addNewTenant")
    public ResponseEntity<Tenants> addNewTenant(@RequestBody Tenants tenants){
        return ResponseEntity.ok(tenantService.addTenant(tenants));
    }
    @DeleteMapping("/deleteTenant")
    public String deleteTenant(@RequestParam String email) throws Exception {
        if(tenantService.deleteTenant(email) == 1){
            return "Delete successful";
        }
        return "Delete not successful";
    }
    @PostMapping("/getTenantByRoomId")
    public ResponseEntity<List<Tenants>> getTenantByRoomId(@RequestParam int roomId){
        return ResponseEntity.ok(tenantService.getTenantByRoomId(roomId));
    }
    @PostMapping("/getTenantByEmail")
    public ResponseEntity<Tenants> getTenantByEmail(@RequestParam String email){
        return ResponseEntity.ok(tenantService.getTenantByEmail(email));
    }
    @PostMapping("/updateTenants")
    public void updateTenants(@RequestBody List<Tenants> tenantsList){
        tenantService.updateTenant(tenantsList);
    }
}