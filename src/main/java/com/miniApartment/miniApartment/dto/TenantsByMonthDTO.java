package com.miniApartment.miniApartment.dto;

import lombok.Getter;
import lombok.Setter;

public class TenantsByMonthDTO {
    @Getter
    @Setter
    int month;

    @Getter
    @Setter
    long tenants;

    public TenantsByMonthDTO() {
        this.month = 0;
        this.tenants = 0;
    }

    public TenantsByMonthDTO(int month, long tenants) {
        this.month = month;
        this.tenants = tenants;
    }
}