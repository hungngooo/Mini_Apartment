package com.miniApartment.miniApartment.Services;

import com.miniApartment.miniApartment.Entity.Contract;
import com.miniApartment.miniApartment.Entity.IDemoExample;
import com.miniApartment.miniApartment.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ContractService {

    public Page<Contract> getAllContract(Integer pageNo, Integer pageSize, String keySearch) throws Exception;

    Contract getContractByContractId(String contractId);

    public List<IDemoExample> getExample();

    public Contract getContractByRoom(int id);

    RentalFeeOfContractDTO getRepesentativeByRoomId(int roomId, int month);

    ContractResponseDTO addNewContract(CreateContractDTO createContractDTO);

    Contract findContractById(int id);

    Contract updateContract(int roomId, UpdateContractDTO updateContractDTO);
    List<TenantsByMonthDTO> countTenantsEachMonth();
    String uploadContractPdf(int roomId, MultipartFile file);
    List<TenantThisMonthDTO> getRoomTenantInfoForCurrentMonth(int currentMonth);
}

