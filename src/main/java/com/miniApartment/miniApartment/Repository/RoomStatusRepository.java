package com.miniApartment.miniApartment.Repository;

import com.miniApartment.miniApartment.Entity.IRoomByStatus;
import com.miniApartment.miniApartment.Entity.RoomEntity;
import com.miniApartment.miniApartment.Entity.RoomStatus;
import com.miniApartment.miniApartment.dto.RoomDetailListDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomStatusRepository extends JpaRepository<RoomStatus, Long> {
    RoomStatus findRoomStatusByRoomIdAndMonthAndYear(int roomId, int month, int year);
    @Query(value = "select r.roomId from RoomStatus r where (r.month = :month and r.year = :year) and (r.roomStatus = 'reserved' or r.roomStatus = 'occupied')")
    List<Integer> getRoomEntityAvailable(@Param("month") int month, @Param("year") int year);
    @Query(value = "SELECT " +
            "SUM(CASE WHEN r.roomStatus = 'vacant' THEN 1 ELSE 0 END) AS vacantCount, " +
            "SUM(CASE WHEN r.roomStatus = 'occupied' THEN 1 ELSE 0 END) AS occupiedCount, " +
            "SUM(CASE WHEN r.roomStatus = 'reserved' THEN 1 ELSE 0 END) AS reservedCount " +
            "FROM RoomStatus r where r.month = :month and r.year = :year")
    IRoomByStatus countRoomStatus(@Param("month") int month, @Param("year") int year);
    @Query(value = "select new com.miniApartment.miniApartment.dto.RoomDetailListDTO(r.roomId,r.rentalFee,rs.roomStatus) from RoomEntity r" +
            " join RoomStatus rs on r.roomId = rs.roomId where rs.month = :month and rs.year = :year")
    List<RoomDetailListDTO> getRoomDetail(@Param("month") int month, @Param("year") int year);
}
