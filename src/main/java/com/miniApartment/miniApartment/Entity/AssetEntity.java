package com.miniApartment.miniApartment.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Data
@Getter
@Setter
@Entity
@Table(name = "assetTable")
public class AssetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    @Column(name = "year")
    private int year;
    @Column(name = "month")
    private int month;
    @Column(name = "roomId")
    private int roomId;
    @Column(name = "itemName")
    private String item;
    @Column(name = "unit")
    private String unit;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "value")
    private int value;
    @Column(name = "maintCycle")
    private int maintCycle;
    @Column(name = "maintDate")
    private  String maintDate;
    @Column(name = "maintStatus")
    private String maintStatus;

    public AssetEntity(BigInteger id, int year, int month, int roomId, String item, String unit, int quantity, int value, int maintCycle, String maintDate, String maintStatus) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.roomId = roomId;
        this.item = item;
        this.unit = unit;
        this.quantity = quantity;
        this.value = value;
        this.maintCycle = maintCycle;
        this.maintDate = maintDate;
        this.maintStatus = maintStatus;
    }
}
