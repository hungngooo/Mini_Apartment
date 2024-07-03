package com.miniApartment.miniApartment.Services;

import com.miniApartment.miniApartment.Entity.Contract;
import com.miniApartment.miniApartment.Repository.ContractRepository;
import com.miniApartment.miniApartment.Response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractService {
    @Autowired
    private ContractRepository contractRepository;

    public List<Contract> getAllContract() {
        return contractRepository.findAll();
    }

}
