package com.miniApartment.miniApartment.Services;

import com.miniApartment.miniApartment.Entity.Contract;
import com.miniApartment.miniApartment.Entity.ContractDetail;
import com.miniApartment.miniApartment.Entity.IDemoExample;
import com.miniApartment.miniApartment.Entity.Tenants;
import com.miniApartment.miniApartment.Repository.ContractRepository;
import com.miniApartment.miniApartment.Response.Response;
import com.miniApartment.miniApartment.dto.CreateContractDTO;
import com.miniApartment.miniApartment.dto.RentalFeeOfContractDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ContractService {
    public Page<Contract> getAllContract(Integer pageNo, Integer pageSize, String keySearch)throws Exception;
    Page<Contract> getContractByContractId(Integer pageNo, Integer pageSize, int roomId);
    public List<IDemoExample> getExample();
    public Contract getContractByRoom(int id);
    RentalFeeOfContractDTO getRepesentativeByRoomId(int roomId);
    Contract addNewContract(CreateContractDTO createContractDTO);
}

