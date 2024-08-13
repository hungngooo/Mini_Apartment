package com.miniApartment.miniApartment.Controller;


import com.miniApartment.miniApartment.Entity.IListPayment;
import com.miniApartment.miniApartment.Response.EHttpStatus;
import com.miniApartment.miniApartment.Response.Response;
import com.miniApartment.miniApartment.Services.PaymentService;
import com.miniApartment.miniApartment.dto.PaymentQrDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/payment_citizen")
@CrossOrigin
@PreAuthorize("hasRole('ROLE_CITIZEN')")
public class PaymentCitizenController {
    @Autowired
    private PaymentService paymentService;

    //    @GetMapping("/getPaymentByYear")
//    public Response<Page<IListPayment>> getPaymentByYear(@RequestParam(defaultValue = "0") Integer pageNo,
//                                                         @RequestParam(defaultValue = "10") Integer pageSize,
//                                                         @RequestParam String year){
//        return new Response<>(EHttpStatus.OK, paymentService.getPaymentByYear(pageNo,pageSize,year));
//    }
    @GetMapping("/getPaymentByYearAndRoom")
    public Response<?> getPaymentByYearAndRoom(@RequestParam String year, @RequestHeader("Authorization") String authHeader) {
        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }
        if (paymentService.getPaymentByYearAndEmail(year, token) == null) {
        return  new Response<>(EHttpStatus.NO_RESULT_FOUND, "not found");
        }
            return new Response<>(EHttpStatus.OK, paymentService.getPaymentByYearAndEmail(year, token));
    }
    @PostMapping("/getPaymentQR")
    public Response<?> getQrPayment(@RequestBody PaymentQrDTO dto){
        return new Response<>(EHttpStatus.OK,paymentService.getQrUrl(dto));
    }
    @PostMapping("/checkPay")
    public Response<?> checkPay(@RequestBody PaymentQrDTO dto){
        return new Response<>(EHttpStatus.OK,paymentService.checkPay(dto));
    }
}
