package com.pidev.esprit.repositories;

import com.pidev.esprit.entities.Role;
import com.pidev.esprit.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
