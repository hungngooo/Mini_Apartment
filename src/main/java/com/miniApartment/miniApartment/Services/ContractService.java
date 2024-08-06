package com.miniApartment.miniApartment.Services;

import com.miniApartment.miniApartment.Entity.Contract;
import com.miniApartment.miniApartment.Entity.IDemoExample;
import com.miniApartment.miniApartment.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

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


    List<TenantThisMonthDTO> getRoomTenantInfoForCurrentMonth(int currentMonth);
}

