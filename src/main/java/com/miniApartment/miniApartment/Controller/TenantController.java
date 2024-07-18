package com.miniApartment.miniApartment.Controller;

import com.miniApartment.miniApartment.Entity.Tenants;
import com.miniApartment.miniApartment.Response.EHttpStatus;
import com.miniApartment.miniApartment.Response.Response;
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
    public Response<Page<Tenants>> getAllTenant(@RequestParam(defaultValue = "0") Integer pageNo,
                                                @RequestParam(defaultValue = "2") Integer pageSize,
                                                @RequestParam String keySearch) throws Exception {
        return new Response<>(EHttpStatus.OK,tenantService.getAllTenants(pageNo,pageSize,keySearch));
    }
    @PostMapping("/addNewTenant")
    public ResponseEntity<Tenants> addNewTenant(@RequestBody Tenants tenants){
        return ResponseEntity.ok(tenantService.addTenant(tenants));
    }
    @DeleteMapping("/deleteTenant")
    public Response<?> deleteTenant(@RequestParam String email) throws Exception {
        if(tenantService.deleteTenant(email) == 1){
            return new Response<>(EHttpStatus.OK,"Delete successful");
        }
        return new Response<>(EHttpStatus.OK,"Delete not successful");
    }
    @PostMapping("/getTenantByRoomId")
    public Response<Page<Tenants>> getTenantByRoomId(@RequestParam(defaultValue = "0") Integer pageNo,
                                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                                     @RequestParam int roomId){
        return new Response<>(EHttpStatus.OK,tenantService.getTenantByRoomId(pageNo,pageSize,roomId));
    }
    @PostMapping("/updateTenants")
    public void updateTenants(@RequestBody List<Tenants> tenantsList){
        tenantService.updateTenant(tenantsList);
    }
}