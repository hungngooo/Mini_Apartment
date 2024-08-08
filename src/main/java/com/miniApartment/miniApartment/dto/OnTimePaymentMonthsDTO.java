package com.miniApartment.miniApartment.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OnTimePaymentMonthsDTO {
    private Integer month;
    private Long rooms;
    private Long onTimeRooms;
    OnTimePaymentMonthsDTO(int month, Long rooms, Long onTimeRooms){
        this.month = month;
        this.rooms = rooms;
        this.onTimeRooms = onTimeRooms;
    }
}
