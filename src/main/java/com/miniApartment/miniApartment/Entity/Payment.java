package com.miniApartment.miniApartment.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    private Long id;
    @Column(name = "roomId")
    private int roomId;
    @Column(name = "year")
    private String year;
    @Column(name = "month")
    private int month;
    @Column(name = "totalFee")
    private double totalFee;
    @Column(name = "paid")
    private double paid;
    @Column(name = "status")
    private String status;
}
