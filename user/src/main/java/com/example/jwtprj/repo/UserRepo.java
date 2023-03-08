package com.example.jwtprj.repo;

import com.example.jwtprj.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepo extends JpaRepository<User,Long> {
    User findByUsername(String username) ;
    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    public Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.PasswordToken = ?1")
    public Optional<User> findByPasswordToken(String token);
    @Query("SELECT u FROM User u WHERE u.verificationCode = ?1")
    public Optional<User> findByVerificationCode(String code);

}
