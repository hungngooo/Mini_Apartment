package com.miniApartment.miniApartment.Services.impl;

import com.miniApartment.miniApartment.Entity.*;
import com.miniApartment.miniApartment.Repository.ContractDetailRepository;
import com.miniApartment.miniApartment.Repository.ContractRepository;
import com.miniApartment.miniApartment.Repository.RoomRepository;
import com.miniApartment.miniApartment.Repository.TenantRepository;
import com.miniApartment.miniApartment.Response.EHttpStatus;
import com.miniApartment.miniApartment.Response.Response;
import com.miniApartment.miniApartment.Services.ContractService;
import com.miniApartment.miniApartment.dto.ContractResponseDTO;
import com.miniApartment.miniApartment.dto.CreateContractDTO;
import com.miniApartment.miniApartment.dto.RentalFeeOfContractDTO;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    public Page<Contract> getContractByContractId(Integer pageNo, Integer pageSize, int roomId) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return contractRepository.getContractByContractId(roomId, paging);
    }


    public List<IDemoExample> getExample() {
        List<IDemoExample> result = contractRepository.getExample();
        return null;
    }

    @Override
    public Contract getContractByRoom(int id) {
        return contractRepository.getContractByRoomId(id);
    }

    @Override
    public RentalFeeOfContractDTO getRepesentativeByRoomId(int roomId) {
        Date date = new Date();
        int month = date.getMonth();
        Contract contract = contractRepository.getRepesentativeByRoomId(roomId);
        RentalFeeOfContractDTO rentalFeeOfContractDTO = new RentalFeeOfContractDTO(contract);
        if (month != contract.getSigninDate().getMonth()) {
            rentalFeeOfContractDTO.setSecurityDeposite(BigDecimal.valueOf(0));
        }
        return rentalFeeOfContractDTO;
    }

    @Override
    public ContractResponseDTO addNewContract(CreateContractDTO createContractDTO) {

        UUID contractNo = UUID.randomUUID();
//        if (tenantRepository.existsByEmail(createContractDTO.getEmail()) ||
//                !validateDate(createContractDTO.getSigninDate(), createContractDTO.getExpireDate()) ||
//                !validateEmail(createContractDTO.getEmail()) ||
//                !validateRoom(createContractDTO.getRoomId(), createContractDTO.getNumberOfTenant()) ||
//                !contactValidate(createContractDTO.getContact())) {
//            throw new IllegalArgumentException("Please check the entered information");
//        }
//        if(!validateCopy(createContractDTO.getCopies())) {
//            throw new IllegalArgumentException("Please check the entered information");
//        }
        // This code is to save to contract

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
        contractRepository.save(contract);
        // This code is to save to tenant
        Tenants tenants = new Tenants();
        tenants.setContractId(String.valueOf(contractNo));
        tenants.setEmail(createContractDTO.getEmail());
        tenants.setRoomId(createContractDTO.getRoomId());
        String representative = createContractDTO.getRepresentative();
        String[] nameParts = representative.split(" ", 2);
        tenants.setFirstName(nameParts[0]); // Lấy chữ đầu tiên
        tenants.setLastName(nameParts.length > 1 ? nameParts[1] : ""); // Lấy phần còn lại của chuỗi
        tenants.setGender(createContractDTO.getGender());
        tenants.setDateOfBirth(createContractDTO.getDateOfBirth());
        tenants.setContact(createContractDTO.getContact());
        tenants.setCitizenId(createContractDTO.getCitizenId());
        tenants.setCreateCitizenIdDate(createContractDTO.getCreateCitizenIdDate());
        tenants.setCreateCitizenIdPlace(createContractDTO.getCreateCitizenIdPlace());
        tenants.setCareer(createContractDTO.getCareer());
        tenants.setLicensePlate(createContractDTO.getLicensePlate());
        tenants.setVehicleType(createContractDTO.getVehicleType());
        tenants.setVehicleColor(createContractDTO.getVehicleColor());
        tenants.setResidenceStatus(createContractDTO.getResidenceStatus());
        tenants.setPlaceOfPermanet(createContractDTO.getPlaceOfPermanet());
        tenantRepository.save(tenants);
        // This code is to save to contractDetail
        ContractDetail contractDetail = new ContractDetail();
        contractDetail.setContractId(String.valueOf(contractNo));
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
        contractDetail.setRelationship(createContractDTO.getRelationship());
        contractDetailRepository.save(contractDetail);
        // Update room status
        RoomEntity roomEntity = roomRepository.findByRoomId(createContractDTO.getRoomId());
        roomEntity.setRoomStatus(true); // Set the new status for the room
        roomRepository.save(roomEntity);
        //set response dto
        ContractResponseDTO responseDTO = new ContractResponseDTO();
        responseDTO.setContractId(createContractDTO.getContractId());
        responseDTO.setContractStatus(createContractDTO.getContractStatus());
        responseDTO.setMessage("Contract is created successfully");
        return responseDTO;

    }
    public boolean validateCopy(int copy) {
        if(copy <= 2) {
            return false;
        }
        return true;
    }
    public boolean validateDate(Date startDate, Date endDate) {
        Date date = new Date();
        if (startDate == null || endDate == null) {
            return false;
        }
        if(startDate.after(endDate)) {
            return false;
        }
        if(endDate.equals(date)) {
            return false;
        }
        return true;
    }
    public boolean contactValidate(String phoneNum) {
        try {
            if (phoneNum.length() == 10 && (phoneNum.startsWith("08") || phoneNum.startsWith("03")
                    || phoneNum.startsWith("09") || phoneNum.startsWith("05") || phoneNum.startsWith("07")))
                return true;
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public boolean validateRoom(int roomNumber, int numberOfPeople) {
        String roomStr = String.valueOf(roomNumber);

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
//    @Override
//    public void addNewContract(
//                               //requestdto
//                                ) {
//        UUID contractNo = UUID.randomUUID();
//        //validate dto request
//
//        //set request dto -> entity
//        //ContractEntity contract = New Contract();
//        //contracty.setContractID(contractNo)
//
////        contractRepository.save(contract);
////tenantsEntity tenants = new ...
//        //tent.contract(contractNo)
////        tenantRepository.save(tenants);
//        //tenent.set(
//
//
////
//    }
}
