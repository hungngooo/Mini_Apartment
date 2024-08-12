package com.miniApartment.miniApartment.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

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
    @Column(name = "createDate")
    private Date createDate;
    @Column(name = "dueDate")
    private Date dueDate;
    @Column(name = "paymentDate")
    private Date paymentDate;
    @Column(name = "paid")
    private double paid;
    @Column(name = "status")
    private String status;
}
