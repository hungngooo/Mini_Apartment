package com.miniApartment.miniApartment.Entity;
import com.miniApartment.miniApartment.dto.UserInfoDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.Date;

@Data
@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "userId")
    private String userId;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "gender")
    private int gender;
    @Column(name = "dateOfBirth")
    private Date dateOfBirth;
    @Column(name = "placeOfPermanet")
    private String placeOfPermanet;
    @Column(name = "email")
    private String email;
    @Column(name = "contact")
    private String contact;
    @Column(name = "password")
    private String password;
    @Column(name = "roleId")
    private int roleId;
    @Column(name = "image")
    private String image;
    @Column(name = "citizenId")
    private String citizenId;
    public User() {
    }

    public User(Long id, String userId, String firstName, String lastName, int gender, Date dateOfBirth, String placeOfPermanet, String email, String contact, String password, int roleId, String image, String citizenId) {
        this.id = id;
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
        this.image = image;
        this.citizenId = citizenId;
    }

    public User(UserInfoDTO userInfoDTO) {
        this.firstName = userInfoDTO.getFirstName();
        this.lastName = userInfoDTO.getLastName();
        this.gender = userInfoDTO.getGender();
        this.dateOfBirth = userInfoDTO.getDateOfBirth();
        this.placeOfPermanet = userInfoDTO.getPlaceOfPermanet();
        this.email = userInfoDTO.getEmail();
        this.contact = userInfoDTO.getContact();
        this.image = userInfoDTO.getImage();
        this.citizenId = userInfoDTO.getCitizenId();
    }


}
