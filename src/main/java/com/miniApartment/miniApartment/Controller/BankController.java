package com.miniApartment.miniApartment.Controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.miniApartment.miniApartment.Services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@RestController
public class BankController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/getPaymentFromBank")
    public void handleWebhook(@RequestBody String payload, @RequestHeader("secure-token") String webhookKey) throws Exception {
        String expectedKey = "FakfLNi92MNKL2n";
        if (!webhookKey.equals(expectedKey)) {
            throw new SecurityException("Invalid webhook key");
        } else {
            paymentService.confirmPayment(payload);
        }
    }
}
