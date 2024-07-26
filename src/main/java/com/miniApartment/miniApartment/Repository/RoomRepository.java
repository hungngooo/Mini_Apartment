package com.miniApartment.miniApartment.Repository;

import com.miniApartment.miniApartment.Entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<RoomEntity,Long> {
    @Query(value = "select r from RoomEntity r where r.roomStatus = true")
    List<RoomEntity> getRoomEntityAvailable();
}
