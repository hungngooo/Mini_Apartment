package com.miniApartment.miniApartment.Services;

import com.miniApartment.miniApartment.Entity.IAssetDetail;
import com.miniApartment.miniApartment.Entity.IAssetRoom;
import com.miniApartment.miniApartment.dto.AssetRoomDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AssetService {
    List<AssetRoomDTO> getAssetRoom(int month, int year);

    Page<IAssetDetail> getAssetDetailByRoom(Integer pageNo, Integer pageSize, int roomId, int month, int year);

    void updateAssetMaintenance(Long id, int maintCycle, String maintDate, String maintStatus);
}
