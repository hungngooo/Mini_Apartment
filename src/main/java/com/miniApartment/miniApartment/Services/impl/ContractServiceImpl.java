package com.miniApartment.miniApartment.Services.impl;

import com.miniApartment.miniApartment.Entity.*;
import com.miniApartment.miniApartment.Repository.ContractDetailRepository;
import com.miniApartment.miniApartment.Repository.ContractRepository;
import com.miniApartment.miniApartment.Repository.RoomRepository;
import com.miniApartment.miniApartment.Repository.TenantRepository;
import com.miniApartment.miniApartment.Services.ContractService;
import com.miniApartment.miniApartment.Services.MinioService;
import com.miniApartment.miniApartment.dto.*;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class ContractServiceImpl implements ContractService {
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private ContractDetailRepository contractDetailRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private MinioService minioService;

    @Override
    public Page<Contract> getAllContract(Integer pageNo, Integer pageSize, String keySearch) throws Exception {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Contract> pageResult;
        if (keySearch == null || keySearch.equals("")) {
            pageResult = contractRepository.findAll(paging);
        } else {
            pageResult = contractRepository.searchContractByRoomId(keySearch, paging);
        }
        if (pageNo > pageResult.getTotalPages()) {
            throw new Exception();
        }
        return pageResult;
    }

    @Override
    public Contract getContractByContractId(String contractId) {
        return contractRepository.findContractByContractId(contractId);
    }


    public List<IDemoExample> getExample() {
        List<IDemoExample> result = contractRepository.getExample();
        return null;
    }

    @Override
    public Contract getContractByRoom(int id) {
        return contractRepository.findContractByRoomId(id);
    }

    @Override
    public RentalFeeOfContractDTO getRepesentativeByRoomId(int roomId, int month) {
        Contract contract = contractRepository.getRepesentativeByRoomId(roomId);
        RentalFeeOfContractDTO rentalFeeOfContractDTO = new RentalFeeOfContractDTO(contract);
        if (month != contract.getSigninDate().getMonth() + 1) {
            rentalFeeOfContractDTO.setSecurityDeposite(BigDecimal.valueOf(0));
        }
        return rentalFeeOfContractDTO;
    }

    @Override
    public ContractResponseDTO addNewContract(CreateContractDTO createContractDTO) {
        Date date = new Date();
        UUID contractNo = UUID.randomUUID();

        if (tenantRepository.existsByEmail(createContractDTO.getEmail()) ||
                !validateDate(createContractDTO.getSigninDate(), createContractDTO.getMoveinDate(), createContractDTO.getExpireDate()) ||
                !validateEmail(createContractDTO.getEmail()) ||
                !validateRoom(createContractDTO.getRoomId(), createContractDTO.getNumberOfTenant()) ||
                !contactValidate(createContractDTO.getContact()) ||
                !validateCopy(createContractDTO.getCopies())) {
            throw new IllegalArgumentException("Please check the entered information");
        }

        // Save Contract
        Contract contract = saveContract(createContractDTO, contractNo);

        // Save Tenants
        List<Tenants> tenants = saveTenants(createContractDTO.getTenants(), contractNo, createContractDTO.getRoomId());

        // Save ContractDetail
        ContractDetail contractDetail = saveContractDetail(createContractDTO, contractNo);

        // Update room status
        updateRoomStatus(createContractDTO.getRoomId());
        // Generate PDF

        // Set response DTO
        return createResponseDTO(contract);
    }

    private void savePdfUrlToDatabase(String fileUrl, int roomId) {
        Contract contract = contractRepository.findContractByRoomId(roomId);
        contract.setContract(fileUrl);
        contractRepository.save(contract);
    }

    private String uploadPdfToMinio(byte[] pdfData, int roomId) {
        try {
            String bucketName = "miniapartment";
            String fileName = "File_Contract_" + roomId;
            MinioClient minioClient = minioService.getMinioClient();
            // Create a temporary file
            Path tempFile = Files.createTempFile(fileName, ".pdf");
            Files.write(tempFile, pdfData);

            // Upload the file to MinIO
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(Files.newInputStream(tempFile), pdfData.length, -1)
                            .contentType("application/pdf")
                            .build()
            );

            // Delete the temporary file
            Files.deleteIfExists(tempFile);

            // Return the file URL
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(fileName)
                            .expiry(60 * 60 * 24) // 1 day expiry
                            .build()
            );

        } catch (Exception e) {
            throw new RuntimeException("Failed to upload PDF to MinIO", e);
        }
    }
    @Override
    public String uploadContractPdf(int roomId, MultipartFile file) {
        try {
            // Convert MultipartFile to byte array
            byte[] pdfData = file.getBytes();

            // Upload the PDF to MinIO and get the URL
            String fileUrl = uploadPdfToMinio(pdfData, roomId);
            String[] urlSplit = fileUrl.split("\\?");
            // Save the PDF URL to the database
            savePdfUrlToDatabase(urlSplit[0], roomId);

            return fileUrl;
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload and save PDF", e);
        }
    }

    private Contract saveContract(CreateContractDTO createContractDTO, UUID contractNo) {
        Contract contract = new Contract();
        contract.setContractId(String.valueOf(contractNo));
        contract.setRoomId(createContractDTO.getRoomId());
        contract.setNumberOfTenant(createContractDTO.getNumberOfTenant());
        contract.setRentalFee(createContractDTO.getRentalFee());
        contract.setSecurityDeposite(createContractDTO.getSecurityDeposite());
        contract.setPaymentCycle(createContractDTO.getPaymentCycle());
        contract.setSigninDate(createContractDTO.getSigninDate());
        contract.setMoveinDate(createContractDTO.getMoveinDate());
        contract.setExpireDate(createContractDTO.getExpireDate());
        contract.setContractStatus(1); // status = 1 là in Lease term, 2 là Approaching Expiration, 3 là Past Expiration
        contract.setRepresentative(createContractDTO.getRepresentative());
        return contractRepository.save(contract);
    }

    private List<Tenants> saveTenants(List<TenantDTO> tenantDTOs, UUID contractNo, int roomId) {
        List<Tenants> savedTenants = new ArrayList<>();
        for (TenantDTO tenantDTO : tenantDTOs) {
            Tenants tenant = new Tenants();
            tenant.setContractId(String.valueOf(contractNo));
            tenant.setRoomId(roomId);
            tenant.setFirstName(tenantDTO.getFirstName());
            tenant.setLastName(tenantDTO.getLastName());
            tenant.setGender(tenantDTO.getGender());
            tenant.setDateOfBirth(tenantDTO.getDateOfBirth());
            tenant.setContact(tenantDTO.getContact());
            tenant.setEmail(tenantDTO.getEmail());
            tenant.setCareer(tenantDTO.getCareer());
            tenant.setLicensePlate(tenantDTO.getLicensePlate());
            tenant.setVehicleType(tenantDTO.getVehicleType());
            tenant.setVehicleColor(tenantDTO.getVehicleColor());
            tenant.setRelationship(tenantDTO.getRelationship());
            tenant.setCitizenId(tenantDTO.getCitizenId());
            tenant.setCreateCitizenIdPlace(tenantDTO.getCreateCitizenIdPlace());
            tenant.setCreateCitizenIdDate(tenantDTO.getCreateCitizenIdDate());
            tenant.setResidenceStatus("Failed");
            tenant.setPlaceOfPermanet(tenantDTO.getPlaceOfPermanet());
            // Lưu tenant vào database và thêm vào danh sách savedTenants
            savedTenants.add(tenantRepository.save(tenant));
        }
        return savedTenants;
    }

    private ContractDetail saveContractDetail(CreateContractDTO createContractDTO, UUID contractNo) {
        ContractDetail contractDetail = new ContractDetail();
        contractDetail.setContractId(String.valueOf(contractNo));
        // Set other contract detail fields
        contractDetail.setTotalArea(createContractDTO.getTotalArea());
        contractDetail.setLandArea(createContractDTO.getLandArea());
        contractDetail.setPublicArea(createContractDTO.getPublicArea());
        contractDetail.setPrivateArea(createContractDTO.getPrivateArea());
        contractDetail.setDevice(createContractDTO.getDevice());
        contractDetail.setOwnerOrigin(createContractDTO.getOwnerOrigin());
        contractDetail.setOwnerLimit(createContractDTO.getOwnerLimit());
        contractDetail.setRights(createContractDTO.getRights());
        contractDetail.setObligations(createContractDTO.getObligations());
        contractDetail.setCommit(createContractDTO.getCommit());
        contractDetail.setCopies(createContractDTO.getCopies());
        return contractDetailRepository.save(contractDetail);
    }

    private ContractResponseDTO createResponseDTO(Contract contract) {
        ContractResponseDTO responseDTO = new ContractResponseDTO();
        responseDTO.setContractId(contract.getContractId());
        responseDTO.setContractStatus(contract.getContractStatus());
        responseDTO.setMessage("Contract is created successfully");
        return responseDTO;
    }

    private void updateRoomStatus(int roomId) {
        RoomEntity roomEntity = roomRepository.findByRoomId(roomId);
        roomEntity.setRoomStatus("reserved");
        roomRepository.save(roomEntity);
    }

    @Override
    public Contract findContractById(int id) {
        return contractRepository.findContractById(id);
    }

    @Override
    public Contract updateContract(int roomId, UpdateContractDTO updateContractDTO) {
        if (!validateDate(updateContractDTO.getSigninDate(), updateContractDTO.getMoveinDate(), updateContractDTO.getExpireDate())) {
            throw new IllegalArgumentException("Please check the entered information");
        }
        Optional<Contract> optionalContract = contractRepository.findByRoomId(roomId);
        if (optionalContract.isPresent()) {
            Contract contract = optionalContract.get();
            if (updateContractDTO.getRentalFee() != null) {
                contract.setRentalFee(updateContractDTO.getRentalFee());
            }
            if (updateContractDTO.getSecurityDeposite() != null) {
                contract.setSecurityDeposite(updateContractDTO.getSecurityDeposite());
            }
            if (updateContractDTO.getPaymentCycle() != 0) {
                contract.setPaymentCycle(updateContractDTO.getPaymentCycle());
            }
            if (updateContractDTO.getSigninDate() != null) {
                contract.setSigninDate(updateContractDTO.getSigninDate());
            }
            if (updateContractDTO.getMoveinDate() != null) {
                contract.setMoveinDate(updateContractDTO.getMoveinDate());
            }
            if (updateContractDTO.getExpireDate() != null) {
                contract.setExpireDate(updateContractDTO.getExpireDate());
            }
            if (updateContractDTO.getContractStatus() != 0) {
                contract.setContractStatus(updateContractDTO.getContractStatus());
            }
            if (updateContractDTO.getRepresentative() != null) {
                contract.setRepresentative(updateContractDTO.getRepresentative());
            }
            return contractRepository.save(contract);
        } else {
            throw new IllegalArgumentException("Contract not found with roomId " + roomId);
        }
    }


    public boolean validateCopy(int copy) {
        if (copy <= 2) {
            return false;
        }
        return true;
    }

    public boolean validateDate(Date startDate, Date moveInDate, Date endDate) {
        Date date = new Date();
        if (startDate == null || endDate == null || moveInDate == null) {
            return false;
        }
        if (moveInDate.before(startDate)) {
            return false;
        }
        if (startDate.after(endDate)) {
            return false;
        }
        if (!endDate.after(date)) {
            return false;
        }
        return true;
    }

    public boolean contactValidate(String phoneNum) {

        if (phoneNum.length() == 10 && (phoneNum.startsWith("08") || phoneNum.startsWith("03")
                || phoneNum.startsWith("09") || phoneNum.startsWith("05") || phoneNum.startsWith("07")))
            return true;
        return false;


    }

    public boolean validateRoom(int roomNumber, int numberOfPeople) {
        String roomStr = String.valueOf(roomNumber);
        boolean existsByRoomId = contractRepository.existsByRoomId(roomNumber);
        if (existsByRoomId) {
            return false;
        }
        // Kiểm tra độ dài của số phòng
        if (roomStr.length() != 3) {
            return false;
        }

        int firstDigit = Integer.parseInt(roomStr.substring(0, 1));
        int lastTwoDigits = Integer.parseInt(roomStr.substring(1));

        // Kiểm tra số phòng bắt đầu bằng 10, 20, 30, 40, 50
        if (firstDigit >= 1 && firstDigit <= 5) {
            // Kiểm tra số phòng kết thúc từ 1 đến 8
            if (lastTwoDigits >= 1 && lastTwoDigits <= 8) {
                return numberOfPeople <= 2;
            }
            // Kiểm tra số phòng kết thúc là 9 hoặc 10
            else if (lastTwoDigits == 9 || lastTwoDigits == 10) {
                return numberOfPeople <= 4;
            }
        }

        return false;
    }

    public boolean validateEmail(String email) {
        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
            return true;
        } catch (AddressException e) {
            return false;
        }
    }

    @Override
    public List<TenantsByMonthDTO> countTenantsEachMonth() {
        List<Object[]> results = contractRepository.countTenantsEachMonth();
        List<TenantsByMonthDTO> tenantsByMonthList = new ArrayList<>();

        for (Object[] result : results) {
            int month = ((Number) result[0]).intValue();
            int tenantCount = ((Number) result[1]).intValue();
            tenantsByMonthList.add(new TenantsByMonthDTO(month, tenantCount));
        }

        return tenantsByMonthList;
    }





    @Override
    public List<TenantThisMonthDTO> getRoomTenantInfoForCurrentMonth(int currentMonth) {
        int lastMonth = currentMonth == 1 ? 12 : currentMonth - 1;
        return contractRepository.findTenantThisMonth(lastMonth, currentMonth);
    }

}
