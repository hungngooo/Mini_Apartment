package com.miniApartment.miniApartment.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class PaymentQrDTO {
    private int roomId;
    private int year;
    private int month;
}
