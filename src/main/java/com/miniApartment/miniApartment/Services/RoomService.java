package com.miniApartment.miniApartment.Services;

import com.miniApartment.miniApartment.Entity.IRoomByStatus;
import com.miniApartment.miniApartment.Entity.RoomEntity;

import java.util.List;

public interface RoomService {
    List<RoomEntity> getAllRoomAvailable();

    List<RoomEntity> getAllRoom();

    int countRoom();

    IRoomByStatus countRoomByStatus();

//    List<Object[]> countRoomByStatus();
}
