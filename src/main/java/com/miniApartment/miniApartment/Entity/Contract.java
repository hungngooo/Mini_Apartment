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
    private int numberOfTenant;
    @Column(precision = 10, scale = 0)
    private BigDecimal rentalFee;
    @Column(precision = 10, scale = 0)
    private BigDecimal securityDeposite;
    private int paymentCycle;
    private Date singinDate;
    private Date moveinDate;
    private Date expireDate;
    private int contractStatus;

    public Contract() {
    }

    public Contract(int contractId, int numberOfTenant, BigDecimal rentalFee, BigDecimal securityDeposite, int paymentCycle, Date singinDate, Date moveinDate, Date expireDate, int contractStatus) {
        this.contractId = contractId;
        this.numberOfTenant = numberOfTenant;
        this.rentalFee = rentalFee;
        this.securityDeposite = securityDeposite;
        this.paymentCycle = paymentCycle;
        this.singinDate = singinDate;
        this.moveinDate = moveinDate;
        this.expireDate = expireDate;
        this.contractStatus = contractStatus;
    }

}
