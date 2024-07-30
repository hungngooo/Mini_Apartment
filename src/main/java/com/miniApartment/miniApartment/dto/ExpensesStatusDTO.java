package com.miniApartment.miniApartment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpensesStatusDTO {
    private String status;
    private String year;
    private int month;
    private int roomId;
}
