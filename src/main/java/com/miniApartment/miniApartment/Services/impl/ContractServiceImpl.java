package com.miniApartment.miniApartment.Services.impl;

import com.miniApartment.miniApartment.Entity.Contract;
import com.miniApartment.miniApartment.Entity.IDemoExample;
import com.miniApartment.miniApartment.Repository.ContractRepository;
import com.miniApartment.miniApartment.Services.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContractServiceImpl implements ContractService {
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
