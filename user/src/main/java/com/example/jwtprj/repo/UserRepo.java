package com.example.jwtprj.repo;

import com.example.jwtprj.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
    User findByUsername(String username) ;

}
