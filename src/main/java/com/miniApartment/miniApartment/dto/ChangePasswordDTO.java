package com.miniApartment.miniApartment.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ChangePasswordDTO {
    private String email;
    private String currentPassword;
    private String newPassword;
}
