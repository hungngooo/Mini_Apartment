package com.miniApartment.miniApartment.Entity;

public interface IRoomByStatus {
    int getVacantCount();
    int getOccupiedCount();
    int getReservedCount();
}
