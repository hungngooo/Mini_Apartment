package com.miniApartment.miniApartment.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class OtpVerificationDTO {
    private String email;
    private String otp;
    private String firstName;
    private String lastName;
    private String password;
}
