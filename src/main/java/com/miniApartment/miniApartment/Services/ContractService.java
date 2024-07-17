package com.miniApartment.miniApartment.Services;

import com.miniApartment.miniApartment.Entity.Contract;
import com.miniApartment.miniApartment.Entity.IDemoExample;
import com.miniApartment.miniApartment.Repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Contract updateContractStatus(int contractId, int contractStatus) {
        Optional<Contract> contract = contractRepository.findById(contractId);
        if (contract.isPresent()) {
            Contract contract1 = contract.get();
            contract1.setContractStatus(contractStatus);
            return contractRepository.save(contract1);
        } else {
            throw new RuntimeException("Contract not found");
        }
    }

    public Contract updateContract(Contract contract, int contractId) {
        Optional<Contract> findContract = contractRepository.findById(contractId);
        if (findContract.isPresent()) {
            Contract existContract = findContract.get();
            existContract.setRoomId(contract.getRoomId());
            existContract.setRepresentative(contract.getRepresentative());
            existContract.setNumberOfTenant(contract.getNumberOfTenant());
            existContract.setRentalFee(contract.getRentalFee());
            existContract.setSecurityDeposite(contract.getSecurityDeposite());
            existContract.setPaymentCycle(contract.getPaymentCycle());
            existContract.setSigninDate(contract.getSigninDate());
            existContract.setMoveinDate(contract.getMoveinDate());
            existContract.setExpireDate(contract.getExpireDate());
            existContract.setContractStatus(contract.getContractStatus());
            Contract contract1 = contractRepository.save(existContract);
            return contract1;
        } else {
            throw new RuntimeException("Contract not found with id " + contractId);
        }
    }

}
