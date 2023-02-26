package com.pidev.esprit.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Utilisateur implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Date birthDay;
    private String phoneNumber;
    private String avatar;
    private Boolean available;
    private Boolean accessD;
    private Boolean accessR;
    @Enumerated(EnumType.STRING)
    private Role role;
}


