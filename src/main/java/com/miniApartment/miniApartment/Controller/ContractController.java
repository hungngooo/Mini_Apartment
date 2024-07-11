package com.miniApartment.miniApartment.Controller;

import com.miniApartment.miniApartment.Entity.Contract;
import com.miniApartment.miniApartment.Entity.IDemoExample;
import com.miniApartment.miniApartment.Services.ContractService;
import org.simpleframework.xml.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/contract")
public class ContractController {
    @Autowired
    private ContractService contractService;

    @GetMapping("/getAllContract")
    public ResponseEntity<List<Contract>> getAllContract() {
        return ResponseEntity.ok(contractService.getAllContract());
    }

    @GetMapping("/getContractByContractId/{contractId}")
    public ResponseEntity<Optional<Contract>> getContractByContractId(@PathVariable int contractId) {
        return ResponseEntity.ok(contractService.getContractById(contractId));
    }



    @GetMapping("/getExample")
    public ResponseEntity<List<IDemoExample>> getExample() {
        return ResponseEntity.ok(contractService.getExample());
    }
}
