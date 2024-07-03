package com.miniApartment.miniApartment.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
@Entity
public class Tenants {
    @Id
    private String email;

    private int roomId;
    private String firstName;
    private String lastName;
    private Boolean gender;
    private Date dateOfBirth;
    private String contact;
    private String career;
    private String licensePlate;
    private String vehicleType;
    private String vehicleColor;
    private String residenceStatus;
    private int contractId;
    private Integer citizenId;
}
