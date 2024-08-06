package com.miniApartment.miniApartment.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Entity
@Table(name = "tenants")
public class Tenants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;
    @Column(name = "roomId")
    private int roomId;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "gender")
    private int gender;
    @Column(name = "dateOfBirth")
    private Date dateOfBirth;
    @Column(name = "contact")
    private String contact;

    @Column(name = "career")
    private String career;
    @Column(name = "licensePlate")
    private String licensePlate;
    @Column(name = "vehicleType")
    private String vehicleType;
    @Column(name = "vehicleColor")
    private String vehicleColor;
    @Column(name = "residenceStatus")
    private String residenceStatus;
    @Column(name = "contractId")
    private String contractId;
    @Column(name = "citizenId")
    private String citizenId;
    @Column(name = "createCitizenIdDate")
    private Date createCitizenIdDate;
    @Column(name = "createCitizenIdPlace")
    private String createCitizenIdPlace;
    @Column(name = "placeOfPermanet")
    private String placeOfPermanet;
    private String relationship;
}
