package com.miniApartment.miniApartment.Services;

import com.miniApartment.miniApartment.Entity.Contract;
import com.miniApartment.miniApartment.Entity.IDemoExample;
import com.miniApartment.miniApartment.Repository.ContractRepository;
import com.miniApartment.miniApartment.Response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ContractService {
    public List<Contract> getAllContract();
    public Optional<Contract> getContractById(int contractId);
    public List<IDemoExample> getExample();
    public Contract getContractByRoom(int id);
}
