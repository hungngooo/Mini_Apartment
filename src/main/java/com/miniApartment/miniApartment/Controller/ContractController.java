package com.miniApartment.miniApartment.Controller;

import com.miniApartment.miniApartment.Entity.Contract;
import com.miniApartment.miniApartment.Entity.IDemoExample;
import com.miniApartment.miniApartment.Response.EHttpStatus;
import com.miniApartment.miniApartment.Response.Response;
import com.miniApartment.miniApartment.Services.ContractService;
import org.simpleframework.xml.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public Response<Page<Contract>> getAllContract(@RequestParam(defaultValue = "0") Integer pageNo,
                                                   @RequestParam(defaultValue = "2") Integer pageSize,
                                                   @RequestParam String keySearch) throws Exception {
        return new Response<>(EHttpStatus.OK,contractService.getAllContract(pageNo,pageSize,keySearch));
    }

    @GetMapping("/getContractByContractId")
    public Response<Page<Contract>> getContractByContractId(@RequestParam(defaultValue = "0") Integer pageNo,
                                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                                            @RequestParam int contractId) {
        return new Response<>(EHttpStatus.OK,contractService.getContractByContractId(pageNo,pageSize,contractId));
    }
    @GetMapping("/getContractByRoom/{roomId}")
    public Response<?> getContractByRoom(@PathVariable int roomId) {
        return new Response<>(EHttpStatus.OK, contractService.getContractByRoom(roomId));
    }
//    @PutMapping("/updateStatus/{contractId}/{status}")
//    public Contract updateStatus(@PathVariable int contractId, @PathVariable int status) {
//        return contractService.updateContractStatus(contractId, status);
//    }
//    @PutMapping("/updateContract/{contractId}")
//    public ResponseEntity<Contract> updateContract(@RequestBody Contract contract, @PathVariable int contractId) {
//        try {
//            Contract updatedContract = contractService.updateContract(contract, contractId);
//            return ResponseEntity.ok(updatedContract);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
    @GetMapping("/getExample")
    public ResponseEntity<List<IDemoExample>> getExample() {
        return ResponseEntity.ok(contractService.getExample());
    }
}
