package com.pidev.esprit.model.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
public class User {
    private Long id;
    private String name;
    private String username;
    private String password;
    private Collection<Role> roles;

}
