package com.miniApartment.miniApartment.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class AssetItemDTO {
    private int year;
    private int month;
    private int roomId;
    private List<ListAssetItemDTO> asset;
}
