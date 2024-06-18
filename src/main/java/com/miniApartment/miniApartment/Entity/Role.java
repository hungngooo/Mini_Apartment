package com.miniApartment.miniApartment.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "role")
public class Role {
    @Id
    @Column(name = "roleId")
    private int Id;
    @Column(name = "roleName")
    private String roleName;
    public Role() {

    }

    public Role(int id, String roleName) {
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
