package com.miniApartment.miniApartment.Controller;

import com.miniApartment.miniApartment.Response.EHttpStatus;
import com.miniApartment.miniApartment.Response.Response;
import com.miniApartment.miniApartment.Services.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/asset")
public class AssetController {
    @Autowired
    private AssetService assetService;

    @GetMapping("/getAssetRoom")
    public Response<?> getAssetRoom(@RequestParam int month, int year){
        return new Response<>(EHttpStatus.OK,assetService.getAssetRoom(month, year));
    }

    @GetMapping("/getAssetDetailByRoom")
    public Response<?> getAssetDetailByRoom(@RequestParam(defaultValue = "0") Integer pageNo,
                                            @RequestParam(defaultValue = "10") Integer pageSize, int roomId, int month, int year){
        return new Response<>(EHttpStatus.OK,assetService.getAssetDetailByRoom(pageNo, pageSize, roomId, month, year));
    }

    @PostMapping("/updateAssetMaintenance")
    public Response<?> updateAssetMaintenance(@RequestParam Long id, int maintCycle, String maintDate, String maintStatus){
        if(assetService.updateAssetMaintenance(id, maintCycle, maintDate, maintStatus)) {
            return new Response<>(EHttpStatus.OK);
        }
        return new Response<>(EHttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/deleteAssetItem")
    public Response<?> deleteAssetItem(@RequestParam Long id){
        if(assetService.deleteAssetItem(id)) {
            return new Response<>(EHttpStatus.OK);
        }
        return new Response<>(EHttpStatus.BAD_REQUEST);
    }
}
