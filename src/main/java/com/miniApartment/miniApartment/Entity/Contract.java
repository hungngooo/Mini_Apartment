package com.miniApartment.miniApartment.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
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
    private int contractId;
    private int roomId;
    @Column(precision = 10, scale = 0)
    private BigDecimal rentalFee;
    @Column(precision = 10, scale = 0)
    private BigDecimal securityDeposite;
    private int paymentCycle;
    private Date signinDate;
    private Date moveinDate;
    private Date expireDate;
    private int contractStatus;
    private String representative;
    private int numberOfTenant;

    public Contract() {
    }

    public Contract(int contractId, int roomId, BigDecimal rentalFee, BigDecimal securityDeposite, int paymentCycle, Date signinDate, Date moveinDate, Date expireDate, int contractStatus, String representative, int numberOfTenant) {
        this.contractId = contractId;
        this.roomId = roomId;
        this.rentalFee = rentalFee;
        this.securityDeposite = securityDeposite;
        this.paymentCycle = paymentCycle;
        this.signinDate = signinDate;
        this.moveinDate = moveinDate;
        this.expireDate = expireDate;
        this.contractStatus = contractStatus;
        this.representative = representative;
        this.numberOfTenant = numberOfTenant;
    }
}
