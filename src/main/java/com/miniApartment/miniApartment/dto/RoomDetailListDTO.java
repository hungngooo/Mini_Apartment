package com.miniApartment.miniApartment.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Data
@Getter
@Setter
public class RoomDetailListDTO {
    private int roomId;
    private BigDecimal rentalFee;
    private String roomStatus;

    public RoomDetailListDTO() {
    }

    public RoomDetailListDTO(int roomId, BigDecimal rentalFee, String roomStatus) {
        this.roomId = roomId;
        this.rentalFee = rentalFee;
        this.roomStatus = roomStatus;
    }
}
