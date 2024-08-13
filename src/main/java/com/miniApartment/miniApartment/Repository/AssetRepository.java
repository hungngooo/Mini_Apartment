package com.miniApartment.miniApartment.Repository;

import com.miniApartment.miniApartment.Entity.AssetEntity;
import com.miniApartment.miniApartment.Entity.IAssetDetail;
import com.miniApartment.miniApartment.Entity.IAssetRoom;
import com.miniApartment.miniApartment.dto.AssetRoomDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Objects;

public interface AssetRepository extends JpaRepository<AssetEntity, Long> {
    @Query(value = "select new com.miniApartment.miniApartment.dto.AssetRoomDTO(r.roomId as roomId, ifnull(sum(a.quantity), 0) as quantity, ifnull(sum(a.value), 0) AS totalValue) from RoomEntity r " +
            "left join AssetEntity a " +
            "on r.roomId = a.roomId and a.year = :year and a.month = :month " +
            "group by r.roomId")
    List<AssetRoomDTO> getAssetRoom(@Param("month") int month, @Param("year") int year);

    @Query(value = "select a.id as id, a.item as item, a.unit as unit, a.quantity as quantity, a.value as value, a.maintCycle as maintCycle, a.maintDate as maintDate, a.maintStatus as maintStatus " +
            "from AssetEntity a " +
            "where a.roomId = :roomId and a.month = :month and a.year = :year")
    Page<IAssetDetail> getAssetDetail(@Param("roomId") int roomId, @Param("month") int month, @Param("year") int year, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE AssetEntity a SET a.maintCycle = :maintCycle, a.maintDate = :maintDate, a.maintStatus = :maintStatus WHERE a.id = :id")
    void updateAssetMaintenance(@Param("id") Long id, @Param("maintCycle") int maintCycle, @Param("maintDate") String maintDate, @Param("maintStatus") String maintStatus);
}
