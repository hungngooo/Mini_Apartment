package com.miniApartment.miniApartment.dto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
@Getter
@Setter
public class SignUpDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int roleId;
}
