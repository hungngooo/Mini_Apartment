package com.miniApartment.miniApartment.Controller;

import com.miniApartment.miniApartment.Response.EHttpStatus;
import com.miniApartment.miniApartment.Response.Response;
import com.miniApartment.miniApartment.Services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/room")
@CrossOrigin
public class RoomController {
    @Autowired
    private RoomService roomService;
    @GetMapping("/getRoomAvailable")
    public Response<?> getRoomAvailable(){
        return new Response<>(EHttpStatus.OK,roomService.getAllRoomAvailable());
    }
    @GetMapping("/getAllRoom")
    public Response<?> getAllRoom(){return new Response<>(EHttpStatus.OK,roomService.getAllRoom());}
    @GetMapping("/roomCount")
    public Response<?> roomCount(){
        return new Response<>(EHttpStatus.OK,roomService.countRoom());
    }

    @GetMapping("/countRoomByStatus")
    public Response<?> countRoomByStatus(){
        return new Response<>(EHttpStatus.OK,roomService.countRoomByStatus());
    }

    @GetMapping("/getRoomDetail")
    public Response<?> getRoomDetail(){
        return new Response<>(EHttpStatus.OK,roomService.getRoomDetail());
    }
}
