package com.miniApartment.miniApartment.dto;

import com.miniApartment.miniApartment.Entity.User;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.Date;
@Setter
@Getter
public class UserInfoDTO {
    private String firstName;
    private String lastName;
    private int gender;
    private Date dateOfBirth;
    private String placeOfPermanet;
    private String email;
    private String contact;
    private String image;
    private String citizenId;
    public UserInfoDTO(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.gender = user.getGender();
        this.dateOfBirth = user.getDateOfBirth();
        this.placeOfPermanet = user.getPlaceOfPermanet();
        this.email = user.getEmail();
        this.contact = user.getContact();
        this.image = user.getImage();
        this.citizenId = user.getCitizenId();
    }

    public UserInfoDTO() {
    }
}
