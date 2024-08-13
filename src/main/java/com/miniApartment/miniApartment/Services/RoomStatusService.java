package com.miniApartment.miniApartment.Services;

import com.miniApartment.miniApartment.Entity.IRoomByStatus;
import com.miniApartment.miniApartment.Entity.RoomEntity;
import com.miniApartment.miniApartment.dto.RoomDetailListDTO;

import java.util.List;

public interface RoomStatusService {

    List<Integer> getAllRoomAvailable(Integer month, Integer year);

    IRoomByStatus countRoomByStatus(int month, int year);

    List<RoomDetailListDTO> getRoomDetail(int month, int year);
}
