package com.example.jwtprj.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Data @Entity @NoArgsConstructor @AllArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;

    private Boolean enabled = false;
    private String verificationCode;
    private String PasswordToken;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();
}
