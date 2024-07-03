package com.miniApartment.miniApartment.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LoginVerifyOtpDTO {
    private String email;
    private String otp;
}
