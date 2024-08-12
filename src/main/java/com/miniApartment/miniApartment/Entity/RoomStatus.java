package com.miniApartment.miniApartment.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Entity
@Getter
@Setter
@Table(name = "roomStatusMonths")
public class RoomStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "year")
    private int year;
    @Column(name = "month")
    private int month;
    @Column(name = "roomId")
    private int roomId;
    @Column(name = "roomStatus")
    private String roomStatus;
}
