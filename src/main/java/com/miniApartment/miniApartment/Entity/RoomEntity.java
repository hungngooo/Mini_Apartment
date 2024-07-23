package com.miniApartment.miniApartment.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "room")
public class RoomEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "roomId")
    private int roomId;
    @Column(name = "roomStatus")
    private  boolean roomStatus;
    @Column(name = "maxTenant")
    private Integer maxTenant;
}
