package com.miniApartment.miniApartment.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
public class Tenants {
    @Column(name = "email")
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

}
