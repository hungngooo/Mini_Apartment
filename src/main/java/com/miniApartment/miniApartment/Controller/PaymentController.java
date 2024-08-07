package com.miniApartment.miniApartment.Controller;


import com.miniApartment.miniApartment.Entity.IListPayment;
import com.miniApartment.miniApartment.Response.EHttpStatus;
import com.miniApartment.miniApartment.Response.Response;
import com.miniApartment.miniApartment.Services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/payment")
@CrossOrigin
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/getPaymentByYear")
    public Response<Page<IListPayment>> getPaymentByYear(@RequestParam(defaultValue = "0") Integer pageNo,
                                                         @RequestParam(defaultValue = "10") Integer pageSize,
                                                         @RequestParam String year){
        return new Response<>(EHttpStatus.OK, paymentService.getPaymentByYear(pageNo,pageSize,year));
    }
    @GetMapping("/getPaymentByYearAndRoom")
    public Response<Page<IListPayment>> getPaymentByYearAndRoom(@RequestParam(defaultValue = "0") Integer pageNo,
                                                         @RequestParam(defaultValue = "10") Integer pageSize,
                                                         @RequestParam String year, @RequestParam int roomId){
        return new Response<>(EHttpStatus.OK, paymentService.getPaymentByYearAndRoom(pageNo,pageSize,year,roomId));
    }
}
