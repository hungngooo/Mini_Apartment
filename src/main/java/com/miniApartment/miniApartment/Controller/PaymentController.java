package com.miniApartment.miniApartment.Controller;

import com.miniApartment.miniApartment.Entity.IListPayment;
import com.miniApartment.miniApartment.Services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/getPaymentByYear")
    public ResponseEntity<List<IListPayment>> getPaymentByYear(@RequestParam String year){
        return ResponseEntity.ok(paymentService.getPaymentByYear(year));
    }
}
