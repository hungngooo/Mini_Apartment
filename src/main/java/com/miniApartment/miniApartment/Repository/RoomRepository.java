package com.miniApartment.miniApartment.Repository;

import com.miniApartment.miniApartment.Entity.RoomEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface RoomRepository extends JpaRepository<RoomEntity,Long> {
    @Query(value = "select r from RoomEntity r where r.roomStatus = reserved and r.roomStatus = occupied")
    List<RoomEntity> getRoomEntityAvailable();
    RoomEntity findByRoomId(int roomId);
    @Query(value = "select count(r) from RoomEntity r")
    int countRoom();
//    @Query(value = "SELECT " +
//            "SUM(CASE WHEN r.roomStatus = 'vacant' THEN 1 ELSE 0 END) AS vacantCount, " +
//            "SUM(CASE WHEN r.roomStatus = 'occupied' THEN 1 ELSE 0 END) AS occupiedCount, " +
//            "SUM(CASE WHEN r.roomStatus = 'reserved' THEN 1 ELSE 0 END) AS reservedCount " +
//            "FROM RoomEntity r")
//    List<Object[]> countRoomStatus();
}
