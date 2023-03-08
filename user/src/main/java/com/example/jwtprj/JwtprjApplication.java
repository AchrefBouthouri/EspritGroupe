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

    /*@Bean
    CommandLineRunner run(UserService userService){
        return args -> {
          userService.saveRole(new Role(null,"DORM_MANAGER"));
            userService.saveRole(new Role(null,"RESTAURANT_MANAGER"));
            userService.saveRole(new Role(null,"ADMIN"));
            userService.saveRole(new Role(null,"ETUDIANT"));

            userService.saveUser(new User(null,"Seif KORBI","Seif","1234",new ArrayList<>()));
            userService.saveUser(new User(null,"Sofienne KORBI","Sofienne","1234",new ArrayList<>()));
            userService.saveUser(new User(null,"Yasser SASSI","Yasser","1234",new ArrayList<>()));
            userService.saveUser(new User(null,"Fadhel KAMEL","Fadhel","1234",new ArrayList<>()));

            userService.addRoleToUser("Seif","ETUDIANT");
            userService.addRoleToUser("Yasser","DORM_MANAGER");
            userService.addRoleToUser("Sofienne","ADMIN");
            userService.addRoleToUser("Fadhel","RESTAURANT_MANAGER");
        };
    }*/
}
