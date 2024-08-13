package com.miniApartment.miniApartment.dto;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Data
@Getter
@Setter
public class CreateContractDTO {
    private List<TenantDTO> tenants;
    private int month;
    private int year;
    private String contractId;
    private int roomId;
    private BigDecimal rentalFee;
    private BigDecimal securityDeposite;
    private int paymentCycle;
    private Date signinDate;
    private Date moveinDate;
    private Date expireDate;
    private int contractStatus;
    private String representative;
    private int numberOfTenant;
    private String email;
    private String firstName;
    private String lastName;
    private int gender;
    private Date dateOfBirth;
    private String contact;
    private String career;
    private String licensePlate;
    private String vehicleType;
    private String vehicleColor;
    private String residenceStatus;
    private String citizenId;
    private double totalArea;
    private double landArea;
    private double publicArea;
    private double privateArea;
    private String device;
    private String ownerOrigin;
    private String ownerLimit;
    private String rights;
    private String obligations;
    private String commit;
    private int copies;
    private String contract;
    private Date createCitizenIdDate;
    private String createCitizenIdPlace;
    private String placeOfPermanet;
    private String roomStatus;
    private String relationship;
}
