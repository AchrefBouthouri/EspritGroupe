package com.example.jwtprj.repo;

import com.example.jwtprj.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Long> {
    Role findByName(String name) ;

}
