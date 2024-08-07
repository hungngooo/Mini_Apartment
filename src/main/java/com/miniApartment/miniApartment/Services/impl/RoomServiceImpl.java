package com.miniApartment.miniApartment.Services.impl;

import com.miniApartment.miniApartment.Entity.IRoomByStatus;
import com.miniApartment.miniApartment.Entity.RoomEntity;
import com.miniApartment.miniApartment.Repository.RoomRepository;
import com.miniApartment.miniApartment.Services.RoomService;
import com.miniApartment.miniApartment.dto.RoomDetailListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;
    public List<RoomEntity> getAllRoomAvailable() {
        return roomRepository.getRoomEntityAvailable();
    }
    @Override
    public List<RoomEntity> getAllRoom(){return roomRepository.findAll();}
    @Override
    public int countRoom() {
        return roomRepository.countRoom();
    }
    @Override
    public IRoomByStatus countRoomByStatus() {
        return roomRepository.countRoomStatus();
    }
    @Override
    public List<RoomDetailListDTO> getRoomDetail() {
        return roomRepository.getRoomDetail();
    }
}
