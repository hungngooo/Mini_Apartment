package com.miniApartment.miniApartment.dto;

import com.miniApartment.miniApartment.Entity.Contract;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class RentalFeeOfContractDTO {
    private String representative;
    private BigDecimal rentalFee;
    private BigDecimal securityDeposite;
    private int numberOfTenant;
    public RentalFeeOfContractDTO(Contract contract) {
        this.representative = contract.getRepresentative();
        this.rentalFee = contract.getRentalFee();
        this.securityDeposite = contract.getSecurityDeposite();
        this.numberOfTenant = contract.getNumberOfTenant();
    }
}
