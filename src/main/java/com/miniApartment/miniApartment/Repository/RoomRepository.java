package com.miniApartment.miniApartment.Repository;

import com.miniApartment.miniApartment.Entity.IRoomByStatus;
import com.miniApartment.miniApartment.Entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface RoomRepository extends JpaRepository<RoomEntity,Long> {
    @Query(value = "select r from RoomEntity r where r.roomStatus = 'reserved' or r.roomStatus = 'occupied'")
    List<RoomEntity> getRoomEntityAvailable();
    RoomEntity findByRoomId(int roomId);
    @Query(value = "SELECT count(r) FROM RoomEntity r")
    int countRoom();
    @Query(value = "SELECT " +
            "SUM(CASE WHEN r.roomStatus = 'vacant' THEN 1 ELSE 0 END) AS vacantCount, " +
            "SUM(CASE WHEN r.roomStatus = 'occupied' THEN 1 ELSE 0 END) AS occupiedCount, " +
            "SUM(CASE WHEN r.roomStatus = 'reserved' THEN 1 ELSE 0 END) AS reservedCount " +
            "FROM RoomEntity r")
    IRoomByStatus countRoomStatus();
}
