package com.miniApartment.miniApartment.dto;


import lombok.Data;

import java.util.Date;
@Data
public class PaymentStatusRoomDTO {
    private int roomId;
    private double totalFee;
    private Date dueDate;
    private Date paymentDate;
    private String status;
    PaymentStatusRoomDTO(int roomId, double totalFee, Date dueDate, Date paymentDate, String status){
        this.roomId = roomId;
        this.totalFee = totalFee;
        this.dueDate = dueDate;
        this.paymentDate = paymentDate;
        this.status = status;
    }


}
