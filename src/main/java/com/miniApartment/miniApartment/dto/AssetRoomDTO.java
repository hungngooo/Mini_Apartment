package com.miniApartment.miniApartment.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AssetRoomDTO {
    private int roomId;
    private Long quantity;
    private Long totalValue;
}
