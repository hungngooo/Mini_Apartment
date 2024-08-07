package com.miniApartment.miniApartment.Controller;

import com.miniApartment.miniApartment.Entity.Contract;
import com.miniApartment.miniApartment.Entity.IDemoExample;
import com.miniApartment.miniApartment.Response.EHttpStatus;
import com.miniApartment.miniApartment.Response.Response;
import com.miniApartment.miniApartment.Services.ContractService;
import com.miniApartment.miniApartment.dto.ContractResponseDTO;
import com.miniApartment.miniApartment.dto.CreateContractDTO;
import com.miniApartment.miniApartment.dto.UpdateContractDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return new Response<>(EHttpStatus.OK, contractService.getAllContract(pageNo, pageSize, keySearch));
    }

    @GetMapping("/getContractByContractId")
    public Response<?> getContractByContractId(@RequestParam String contractId) {
        return new Response<>(EHttpStatus.OK, contractService.getContractByContractId(contractId));
    }

    @GetMapping("/getContractByRoom/{roomId}")
    public Response<?> getContractByRoom(@PathVariable int roomId) {
        return new Response<>(EHttpStatus.OK, contractService.getContractByRoom(roomId));
    }

    @GetMapping("/getRepesentative")
    public Response<?> getRepesentativeByRoomId(@RequestParam int roomId, @RequestParam int month) {
        return new Response<>(EHttpStatus.OK, contractService.getRepesentativeByRoomId(roomId, month));
    }

    @PostMapping("/addNewContract")
    public Response<?> addNewContract(@RequestBody CreateContractDTO createContractDTO) {
        ContractResponseDTO responseDTO;
        try {
            responseDTO = contractService.addNewContract(createContractDTO);
        } catch (Exception e) {
            return new Response<>(EHttpStatus.BAD_REQUEST, e.getMessage());
        }
        return new Response<>(EHttpStatus.OK, responseDTO);
    }

    @GetMapping("/findContractById")
    public Response<?> findContractByid(@RequestParam int id) {
        return new Response<>(EHttpStatus.OK, contractService.findContractById(id));
    }

    @PutMapping("/updateContract")
    public Response<?> updateContract(@RequestParam int id, @RequestBody UpdateContractDTO updateContractDTO) {
        Contract updatedContract = contractService.updateContract(id, updateContractDTO);
        return new Response<>(EHttpStatus.OK, updatedContract);
    }

    @GetMapping("/getExample")
    public ResponseEntity<List<IDemoExample>> getExample() {
        return ResponseEntity.ok(contractService.getExample());
    }

    @GetMapping("/countTenantsEachMonth")
    public Response<?> countTenantsEachMonth() {
        return new Response<>(EHttpStatus.OK, contractService.countTenantsEachMonth());
    }

    @GetMapping("/getRoomTenantThisMonth")
    public Response<?> getRoomTenantInfoForCurrentMonth(@RequestParam int currentMonth) {
        return new Response<>(EHttpStatus.OK, contractService.getRoomTenantInfoForCurrentMonth(currentMonth));
    }
}
