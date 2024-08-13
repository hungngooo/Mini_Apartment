package com.miniApartment.miniApartment.Entity;

import com.miniApartment.miniApartment.dto.RentalFeeOfContractDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@Entity
@Table(name = "contract")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private String contractId;
    private int roomId;
    @Column(precision = 10, scale = 0)
    private BigDecimal rentalFee;
    @Column(precision = 10, scale = 0)
    private BigDecimal securityDeposite;
    private int paymentCycle;
    private Date signinDate;
    private Date moveinDate;
    private Date expireDate;
    private String contract;
    private int contractStatus;
    private String representative;
    private int numberOfTenant;

    public Contract() {
    }

    public Contract(BigInteger id, String contractId, int roomId, BigDecimal rentalFee,String contract, BigDecimal securityDeposite, int paymentCycle, Date signinDate, Date moveinDate, Date expireDate, int contractStatus, String representative, int numberOfTenant) {
        this.id = id;
        this.contractId = contractId;
        this.roomId = roomId;
        this.rentalFee = rentalFee;
        this.securityDeposite = securityDeposite;
        this.paymentCycle = paymentCycle;
        this.signinDate = signinDate;
        this.contract = contract;
        this.moveinDate = moveinDate;
        this.expireDate = expireDate;
        this.contractStatus = contractStatus;
        this.representative = representative;
        this.numberOfTenant = numberOfTenant;
    }

    public Contract(String contractId) {
        this.contractId = contractId;
    }

    public Contract(RentalFeeOfContractDTO dto) {
        this.rentalFee = dto.getRentalFee();
        this.securityDeposite = dto.getSecurityDeposite();
        this.representative = dto.getRepresentative();
    }
}
