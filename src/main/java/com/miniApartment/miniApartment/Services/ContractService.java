package com.miniApartment.miniApartment.Services;

import com.miniApartment.miniApartment.Entity.Contract;
import com.miniApartment.miniApartment.Entity.IDemoExample;
import com.miniApartment.miniApartment.dto.RentalFeeOfContractDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ContractService {
    List<Contract> getAllContract();
    Optional<Contract> getContractById(int contractId);
    List<IDemoExample> getExample();

    RentalFeeOfContractDTO getRepesentativeByRoomId(int roomId);
}
