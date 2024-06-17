package com.miniApartment.miniApartment.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Role")
public class role {
    @Id
    private int Id;
    private String roleName;
    public role() {

    }

    public role(int id, String roleName) {
        Id = id;
        this.roleName = roleName;
    }


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
