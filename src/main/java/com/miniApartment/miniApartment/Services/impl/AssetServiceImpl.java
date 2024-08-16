package com.miniApartment.miniApartment.Services.impl;

import com.miniApartment.miniApartment.Entity.AssetEntity;
import com.miniApartment.miniApartment.Entity.IAssetDetail;
import com.miniApartment.miniApartment.Entity.IAssetRoom;
import com.miniApartment.miniApartment.Repository.AssetRepository;
import com.miniApartment.miniApartment.Services.AssetService;
import com.miniApartment.miniApartment.dto.AssetItemDTO;
import com.miniApartment.miniApartment.dto.AssetRoomDTO;
import com.miniApartment.miniApartment.dto.ListAssetItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssetServiceImpl implements AssetService {
    @Autowired
    private AssetRepository assetRepository;
    @Override
    public List<AssetRoomDTO> getAssetRoom(int month, int year) {

        return assetRepository.getAssetRoom(month, year);
    }
    @Override
    public Page<IAssetDetail> getAssetDetailByRoom(Integer pageNo, Integer pageSize, int roomId, int month, int year) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return assetRepository.getAssetDetail(roomId, month, year, paging);
    }

    @Override
    public boolean updateAssetMaintenance(Long id, int maintCycle, String maintDate, String maintStatus) {
        try {
            assetRepository.updateAssetMaintenance(id, maintCycle, maintDate, maintStatus);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteAssetItem(Long id) {
        try {
            assetRepository.deleteAssetItem(id);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public List<AssetEntity> addNewItem(AssetItemDTO assetItemDTO) {
        List<AssetEntity> assetEntities = saveNewItem(assetItemDTO.getAsset(),assetItemDTO.getRoomId(),assetItemDTO.getYear(),assetItemDTO.getMonth());
        return assetEntities;
    }
    private List<AssetEntity> saveNewItem(List<ListAssetItemDTO> assetItemDTOS, int roomId, int year, int month) {
        List<AssetEntity> savedItem = new ArrayList<>();
        for(ListAssetItemDTO assetItemDTO : assetItemDTOS) {
            AssetEntity assetEntity = new AssetEntity();
            assetEntity.setYear(year);
            assetEntity.setMonth(month);
            assetEntity.setRoomId(roomId);
            assetEntity.setItem(assetItemDTO.getItemName());
            assetEntity.setUnit(assetItemDTO.getUnit());
            assetEntity.setQuantity(assetItemDTO.getQuantity());
            assetEntity.setValue(assetItemDTO.getValue());
            assetEntity.setMaintCycle(assetItemDTO.getMaintCycle());
            assetEntity.setMaintDate(assetItemDTO.getMaintDate());
            assetEntity.setMaintStatus(assetItemDTO.getMaintStatus());
            savedItem.add(assetRepository.save(assetEntity));
        }
        return savedItem;
    }
}
