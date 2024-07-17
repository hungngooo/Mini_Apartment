package com.miniApartment.miniApartment.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
public class Payment {
    @Id
    private int roomId;
    private String year;
    private int month;
    private double totalCost;
    private String status;
}
