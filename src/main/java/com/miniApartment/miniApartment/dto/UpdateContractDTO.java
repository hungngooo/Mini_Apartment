package com.miniApartment.miniApartment.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
@Data
@Getter
@Setter
public class UpdateContractDTO {
    private BigDecimal rentalFee;
    private BigDecimal securityDeposite;
    private int paymentCycle;
    private Date signinDate;
    private Date moveinDate;
    private Date expireDate;
    private int contractStatus;
    private String representative;
}
