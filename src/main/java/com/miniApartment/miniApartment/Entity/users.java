package com.miniApartment.miniApartment.Entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "Users")
public class users {
    @Id
    private String userId;
    private String firstName;
    private String lastName;
    private Boolean gender;
    private Date dateOfBirth;
    private String placeOfPermanet;
    private String email;
    private String contact;
    private String password;
    private int roleId;

    public users() {
    }

    public users(String userId, String firstName, String lastName, Boolean gender, Date dateOfBirth, String placeOfPermanet, String email, String contact, String password, int roleId) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.placeOfPermanet = placeOfPermanet;
        this.email = email;
        this.contact = contact;
        this.password = password;
        this.roleId = roleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPlaceOfPermanet() {
        return placeOfPermanet;
    }

    public void setPlaceOfPermanet(String placeOfPermanet) {
        this.placeOfPermanet = placeOfPermanet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
