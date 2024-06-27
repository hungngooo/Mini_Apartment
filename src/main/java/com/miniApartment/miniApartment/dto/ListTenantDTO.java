package com.miniApartment.miniApartment.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@Data
public class ListTenantDTO {
    @Id
    private String email;
    private int roomId;
    private String career;
    private String licensePlate;
    private String vehicleType;
    private String vehicleColor;
    private boolean representative;
    private String residenceStatus;
    private int contractId;
    private String userId;
    private String firstName;
    private String lastName;
    private Boolean gender;
    private Date dateOfBirth;
    private String placeOfPermanet;
    private String contact;
    private String password;
    private int roleId;
    private String image;

    public ListTenantDTO() {
    }

    public ListTenantDTO(String email, String userId, int roomId, String career, String licensePlate, String vehicleType, String vehicleColor, boolean representative, String residenceStatus, int contractId, String firstName, String lastName, Boolean gender, Date dateOfBirth, int roleId) {
        this.email = email;
        this.roomId = roomId;
        this.career = career;
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
        this.vehicleColor = vehicleColor;
        this.representative = representative;
        this.residenceStatus = residenceStatus;
        this.contractId = contractId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.roleId = roleId;
        this.userId = userId;
    }
}
