package com.miniApartment.miniApartment.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class OtpForgetPasswordDTO {
    private String email;
    private String otp;
    private String newPassword;
    private String confirmPassword;
}
