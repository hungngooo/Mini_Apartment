package com.miniApartment.miniApartment.dto;

import com.miniApartment.miniApartment.Entity.User;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
public class UserInfoDTO {
    private String firstName;
    private String lastName;
    private Boolean gender;
    private Date dateOfBirth;
    private String placeOfPermaet;
    private String email;
    private String contact;
    private String image;

    public UserInfoDTO(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.gender = user.getGender();
        this.dateOfBirth = user.getDateOfBirth();
        this.placeOfPermaet = user.getPlaceOfPermanet();
        this.email = user.getEmail();
        this.contact = user.getContact();
        this.image = user.getImage();
    }
}
