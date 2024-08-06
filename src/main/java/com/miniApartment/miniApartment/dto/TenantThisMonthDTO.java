package com.miniApartment.miniApartment.dto;

import lombok.Data;

@Data
public class TenantThisMonthDTO {
    private Integer roomId;
    private Integer maxTenant;
    private Long numberOfTenantLastMonth;
    private Long numberOfTenantThisMonth;


    public TenantThisMonthDTO(Integer roomId, Integer maxTenant, Long numberOfTenantLastMonth, Long numberOfTenantThisMonth) {
        this.roomId = roomId;
        this.maxTenant = maxTenant;
        this.numberOfTenantLastMonth = numberOfTenantLastMonth;
        this.numberOfTenantThisMonth = numberOfTenantThisMonth;
    }
}
