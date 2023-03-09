package com.example.jwtprj;

import com.example.jwtprj.Service.UserService;
import com.example.jwtprj.domain.Role;
import com.example.jwtprj.domain.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
@EnableEurekaClient
public class JwtprjApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtprjApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder() ;
    }

    @Bean
    CommandLineRunner run(UserService userService){
        return args -> {
          userService.saveRole(new Role(null,"USER"));
            userService.saveRole(new Role(null,"MANAGER"));
            userService.saveRole(new Role(null,"ADMIN"));
            userService.saveRole(new Role(null,"SUPER_ADMIN"));

            userService.saveUser(new User(null,"John Travolta","John","1234",new ArrayList<>()));
            userService.saveUser(new User(null,"Will Smith","Will","1234",new ArrayList<>()));
            userService.saveUser(new User(null,"Jim Carry","Jim","1234",new ArrayList<>()));
            userService.saveUser(new User(null,"Arnold Schwarzenegger","Arnold","1234",new ArrayList<>()));

            userService.addRoleToUser("John","USER");
            userService.addRoleToUser("John","MANAGER");
            userService.addRoleToUser("Will","Manager");
            userService.addRoleToUser("Jim","ADMIN");
            userService.addRoleToUser("Arnold","SUPER_ADMIN");
            userService.addRoleToUser("Arnold","ADMIN");
            userService.addRoleToUser("Arnold","USER");
        };
    }
}
