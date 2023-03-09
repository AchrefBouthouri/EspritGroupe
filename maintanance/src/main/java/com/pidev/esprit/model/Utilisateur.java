package com.pidev.esprit.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

//@Entity
@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@Builder
public class Utilisateur implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    @Temporal(TemporalType.DATE)
    private Date birthDay;
    private String phoneNumber;
    private String avatar;
    private Boolean available;
    private Boolean accessD;
    private Boolean accessR;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private Type type;

    public Utilisateur(long id, String firstname, String lastname, String email, String password, Date birthDay, String phoneNumber, String avatar, Boolean available, Boolean accessD, Boolean accessR, Role role, Type type) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.birthDay = birthDay;
        this.phoneNumber = phoneNumber;
        this.avatar = avatar;
        this.available = available;
        this.accessD = accessD;
        this.accessR = accessR;
        this.role = role;
        this.type = type;
    }
}
