package com.pidev.esprit.services;

import com.pidev.esprit.entities.Role;
import com.pidev.esprit.entities.User;

import java.util.List;

public interface UserService {

        User saveUser(User user);
        Role saveRole(Role role);
        void addRoleToUser(String username, String roleName);
        User getUser(String username);
        List<User>getUsers();
        User updateUser (User user,Long id) ;
}
