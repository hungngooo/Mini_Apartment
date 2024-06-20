package com.miniApartment.miniApartment.dto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ForgetPasswordDTO {
    private String email;
    private String password;
    private String rePassword;
}
