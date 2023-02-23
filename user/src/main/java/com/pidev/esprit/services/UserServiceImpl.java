package com.pidev.esprit.services;

import com.pidev.esprit.entities.Role;
import com.pidev.esprit.entities.User;
import com.pidev.esprit.repositories.RoleRepository;
import com.pidev.esprit.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService{


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(User User, Long id) {
        User userForm = userRepository.findById(id).get();
        userForm.setName(User.getName());
        userForm.setEmail(User.getEmail());
        userForm.setUsername(User.getUsername());
        userForm.setPassword(User.getPassword());
        return userRepository.save(userForm);
    }
}
