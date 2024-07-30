package com.miniApartment.miniApartment.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ContractResponseDTO {
    private String contractId;
    private int contractStatus;
    private String message;
}
