package com.miniApartment.miniApartment.Services;

import com.miniApartment.miniApartment.Entity.IRoomByStatus;
import com.miniApartment.miniApartment.Entity.RoomEntity;
import com.miniApartment.miniApartment.dto.RoomDetailListDTO;

import java.util.List;

public interface RoomService {


    List<RoomEntity> getAllRoom();


    int countRoom();


//    List<Object[]> countRoomByStatus();
}
