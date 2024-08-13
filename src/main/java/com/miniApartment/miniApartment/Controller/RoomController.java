package com.miniApartment.miniApartment.Controller;

import com.miniApartment.miniApartment.Response.EHttpStatus;
import com.miniApartment.miniApartment.Response.Response;
import com.miniApartment.miniApartment.Services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/room")
@CrossOrigin
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping("/getAllRoom")
    public Response<?> getAllRoom(){return new Response<>(EHttpStatus.OK,roomService.getAllRoom());}
    @GetMapping("/roomCount")
    public Response<?> roomCount(){
        return new Response<>(EHttpStatus.OK,roomService.countRoom());
    }

}
