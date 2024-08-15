package com.miniApartment.miniApartment.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "expensesDetail")
public class ExpensesDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paymentId")
    private Long id;
    @Column(name = "roomId")
    private int roomId;
    @Column(name = "year")
    private String year;
    @Column(name = "month")
    private int month;
    @Column(name = "rentalFee")
    private double rentalFee;
    @Column(name = "electricity")
    private double electricity;
    @Column(name = "electricPreviousMeter")
    private double electricPreviousMeter;
    @Column(name = "electricCurrentMeter")
    private double electricCurrentMeter;
    @Column(name = "water")
    private double water;
    @Column(name = "waterPreviousMeter")
    private double waterPreviousMeter;
    @Column(name = "waterCurrentMeter")
    private double waterCurrentMeter;
    @Column(name = "internet")
    private double internet;
    @Column(name = "service")
    private double service;
    @Column(name = "securityDeposite")
    private double securityDeposite;
    @Column(name = "debt")
    private double debt;
    @Column(name = "fine")
    private double fine;
    @Column(name = "status")
    private String status;
    @Column(name = "createDate")
    private Date createDate;
    @Column(name = "dueDate")
    private Date dueDate;
    public ExpensesDetailEntity() {
    }
}
