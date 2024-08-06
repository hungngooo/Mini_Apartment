package com.miniApartment.miniApartment.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Data
@Getter
@Setter
public class TenantDTO {
    private String firstName;
    private String lastName;
    private int gender;
    private Date dateOfBirth;
    private String contact;
    private String email;
    private String career;
    private String licensePlate;
    private String vehicleType;
    private String vehicleColor;
    private String relationship;
    private String citizenId;
    private String residentStatus;
    private Date createCitizenIdDate;
    private String createCitizenIdPlace;
    private String placeOfPermanet;
}
