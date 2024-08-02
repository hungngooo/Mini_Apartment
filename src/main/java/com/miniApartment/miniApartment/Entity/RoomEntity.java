package com.miniApartment.miniApartment.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Entity
@Getter
@Setter
@Table(name = "room")
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "roomId")
    private int roomId;

    @Column(name = "roomStatus")
    private String roomStatus;
    @Column(name = "maxTenant")
    private Integer maxTenant;
    @Column(name = "rentalFee")
    private BigDecimal rentalFee;



}
