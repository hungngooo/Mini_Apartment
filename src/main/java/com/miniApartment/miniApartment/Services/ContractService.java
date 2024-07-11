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
public class ContractService {
    @Autowired
    private ContractRepository contractRepository;

    public List<Contract> getAllContract() {
        return contractRepository.findAll();
    }
    public Optional<Contract> getContractById(int contractId) {
        return contractRepository.findById(contractId);
    }

    public List<IDemoExample> getExample() {
        List<IDemoExample> result = contractRepository.getExample();
        return null;
    }


}
