package com.miniApartment.miniApartment.Services.impl;

import com.miniApartment.miniApartment.Entity.IRoomByStatus;
import com.miniApartment.miniApartment.Entity.RoomEntity;
import com.miniApartment.miniApartment.Repository.RoomStatusRepository;
import com.miniApartment.miniApartment.Services.RoomStatusService;
import com.miniApartment.miniApartment.dto.RoomDetailListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class RoomStatusServiceImpl implements RoomStatusService {
    @Autowired
    private RoomStatusRepository roomStatusRepository;
    @Override
    public List<Integer> getAllRoomAvailable(Integer month, Integer year) {
        LocalDate currentDate = LocalDate.now();
        if (month == null) {
            month = currentDate.getMonthValue();
        }
        if (year == null) {
            year = currentDate.getYear();
        }
        return roomStatusRepository.getRoomEntityAvailable(month, year);
    }
    @Override
    public IRoomByStatus countRoomByStatus(int month, int year) {
        return roomStatusRepository.countRoomStatus(month, year);
    }
    @Override
    public List<RoomDetailListDTO> getRoomDetail(int month, int year) {
        return roomStatusRepository.getRoomDetail(month, year);
    }
}
