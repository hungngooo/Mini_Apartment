package com.miniApartment.miniApartment.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Data
@Getter
@Setter
public class ListAssetItemDTO {
    private List<ListAssetItemDTO> assetItemDTO;
    private String itemName;
    private String unit;
    private int quantity;
    private int value;
    private int maintCycle;
    private String maintDate;
    private String maintStatus;
}
