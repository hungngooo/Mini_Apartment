package com.miniApartment.miniApartment.Entity;

public interface IAssetDetail {
    Integer getId();
    String getItem();
    String getUnit();
    Integer getQuantity();
    Integer getValue();
    Integer getMaintCycle();
    String getMaintDate();
    String getMaintStatus();
}
