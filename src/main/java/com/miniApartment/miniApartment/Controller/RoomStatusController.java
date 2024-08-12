package com.miniApartment.miniApartment.Controller;

import com.miniApartment.miniApartment.Entity.RoomStatus;
import com.miniApartment.miniApartment.Response.EHttpStatus;
import com.miniApartment.miniApartment.Response.Response;
import com.miniApartment.miniApartment.Services.RoomService;
import com.miniApartment.miniApartment.Services.RoomStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roomStatus")
@CrossOrigin
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class RoomStatusController {
    @Autowired
    private RoomStatusService roomStatusService;
    @GetMapping("/getRoomAvailable")
    public Response<?> getRoomAvailable(@RequestParam int month, @RequestParam int year){
        return new Response<>(EHttpStatus.OK,roomStatusService.getAllRoomAvailable(month, year));
    }
    @GetMapping("/countRoomByStatus")
    public Response<?> countRoomByStatus(@RequestParam int month, @RequestParam int year){
        return new Response<>(EHttpStatus.OK,roomStatusService.countRoomByStatus(month, year));
    }

    @GetMapping("/getRoomDetail")
    public Response<?> getRoomDetail(@RequestParam int month, @RequestParam int year){
        return new Response<>(EHttpStatus.OK,roomStatusService.getRoomDetail(month, year));
    }
}
