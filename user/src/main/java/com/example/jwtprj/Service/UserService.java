package com.example.jwtprj.Service;

import com.example.jwtprj.domain.Role;
import com.example.jwtprj.domain.User;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService {
    User UpdateUser(User user , long id);
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    List<User>getUsers();
    public boolean ifEmailExist(String email);
    public void sendPassMail(String userEmail) throws MessagingException, UnsupportedEncodingException;
    public void verifyPassToken(String token,String newPassword);
}
