package com.miniApartment.miniApartment.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "contractdetail")
public class ContractDetail {
    @Id
    private String contractId;
    private double totalArea;
    private double landArea;
    private double publicArea;
    private double privateArea;
    private String device;
    private String ownerOrigin;
    private String ownerLimit;
    private String rights;
    private String obligations;
    private String commit;
    private int copies;
    public ContractDetail() {
    }



    public ContractDetail(String contractId, double totalArea, double landArea, double publicArea, double privateArea, String device, String ownerOrigin, String ownerLimit, String rights, String obligations, String commit, int copies) {
        this.contractId = contractId;
        this.totalArea = totalArea;
        this.landArea = landArea;
        this.publicArea = publicArea;
        this.privateArea = privateArea;
        this.device = device;
        this.ownerOrigin = ownerOrigin;
        this.ownerLimit = ownerLimit;
        this.rights = rights;
        this.obligations = obligations;
        this.commit = commit;
        this.copies = copies;
    }
}
