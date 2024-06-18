package com.miniApartment.miniApartment.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
