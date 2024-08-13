package com.miniApartment.miniApartment.Repository;

import com.miniApartment.miniApartment.Entity.IRoomByStatus;
import com.miniApartment.miniApartment.Entity.RoomEntity;
import com.miniApartment.miniApartment.dto.RoomDetailListDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface RoomRepository extends JpaRepository<RoomEntity,Long> {

    RoomEntity findByRoomId(int roomId);
    @Query(value = "SELECT count(r) FROM RoomEntity r")
    int countRoom();



}
