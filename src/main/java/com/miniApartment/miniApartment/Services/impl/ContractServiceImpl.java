package com.miniApartment.miniApartment.Services.impl;

import com.miniApartment.miniApartment.Entity.Contract;
import com.miniApartment.miniApartment.Entity.IDemoExample;
import com.miniApartment.miniApartment.Entity.Tenants;
import com.miniApartment.miniApartment.Repository.ContractRepository;
import com.miniApartment.miniApartment.Services.ContractService;
import com.miniApartment.miniApartment.dto.RentalFeeOfContractDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ContractServiceImpl implements ContractService {
        @Autowired
    private ContractRepository contractRepository;
    @Override
    public Page<Contract> getAllContract(Integer pageNo, Integer pageSize, String keySearch)throws Exception {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Contract> pageResult;
        if(keySearch == null || keySearch.equals("")) {
            pageResult = contractRepository.findAll(paging);
        } else {
            pageResult = contractRepository.searchContractByRoomId(keySearch,paging);
        }
        if (pageNo > pageResult.getTotalPages()) {
            throw new Exception();
        }
        return pageResult;
    }

    @Override
    public Page<Contract> getContractByContractId(Integer pageNo, Integer pageSize, int roomId) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return contractRepository.getContractByContractId(roomId,paging);
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
    public RentalFeeOfContractDTO getRepesentativeByRoomId(int roomId){
        Date date = new Date();
        int month = date.getMonth();
        Contract contract =  contractRepository.getRepesentativeByRoomId(roomId);
        RentalFeeOfContractDTO rentalFeeOfContractDTO = new RentalFeeOfContractDTO(contract);
        if(month != contract.getSigninDate().getMonth()){
            rentalFeeOfContractDTO.setSecurityDeposite(BigDecimal.valueOf(0));
        }
        return rentalFeeOfContractDTO;
    }
}
