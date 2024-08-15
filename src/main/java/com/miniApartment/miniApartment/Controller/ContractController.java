package com.miniApartment.miniApartment.Controller;

import com.miniApartment.miniApartment.Entity.Contract;
import com.miniApartment.miniApartment.Entity.IDemoExample;
import com.miniApartment.miniApartment.Response.EHttpStatus;
import com.miniApartment.miniApartment.Response.Response;
import com.miniApartment.miniApartment.Services.ContractService;
import com.miniApartment.miniApartment.Services.MinioService;
import com.miniApartment.miniApartment.dto.ContractResponseDTO;
import com.miniApartment.miniApartment.dto.CreateContractDTO;
import com.miniApartment.miniApartment.dto.UpdateContractDTO;
import io.minio.GetObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/contract")
public class ContractController {
    @Autowired
    private ContractService contractService;
    @Autowired
    private MinioService minioService;
    @GetMapping("/getAllContract")
    public Response<Page<Contract>> getAllContract(@RequestParam(defaultValue = "0") Integer pageNo,
                                                   @RequestParam(defaultValue = "10") Integer pageSize,
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
    @PostMapping("/uploadContractPdf/{roomId}")
    public Response<?> uploadContractPdf(@PathVariable int roomId, @RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = contractService.uploadContractPdf(roomId, file);
            return new Response<>(EHttpStatus.OK, fileUrl);
        } catch (Exception e) {
            return new Response<>(EHttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    @GetMapping("/download/{fileName}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable("fileName") String fileName) {
        try {
            InputStream stream = minioService.getMinioClient().getObject(
                    GetObjectArgs.builder()
                            .bucket("miniapartment")
                            .object(fileName)
                            .build()
            );

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(stream));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
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
    @GetMapping("/tenantCount")
    public Response<?> tenantCount(@RequestParam int month){
        return new Response<>(EHttpStatus.OK,contractService.countTenants(month));
    }
}
