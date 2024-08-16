package com.miniApartment.miniApartment.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public interface TenantDetailDTO {
     String getFirstName();
     String getLastName();
     Integer getRoomId();
    Integer getGender();
     Date getDateOfBirth();
     String getContact();
     String getEmail();
     String getCitizenId();
     String getCareer();
     String getLicensePlate();
     Date getMoveinDate();
     Date getExpireDate();
    String getResidenceStatus();
}
