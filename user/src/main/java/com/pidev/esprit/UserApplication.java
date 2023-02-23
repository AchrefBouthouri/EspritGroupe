package com.pidev.esprit;

import com.pidev.esprit.entities.Role;
import com.pidev.esprit.entities.User;
import com.pidev.esprit.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
@EnableEurekaClient
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

//    @Bean
//    CommandLineRunner run(UserService userService){
//        return args -> {
//            userService.saveRole(new Role(null,"USER"));
//            userService.saveRole(new Role(null,"MANAGER"));
//            userService.saveRole(new Role(null,"ADMIN"));
//            userService.saveRole(new Role(null,"SUPER_ADMIN"));
//
//
//
//
//            userService.addRoleToUser("seif","USER");
//            userService.addRoleToUser("korbi","MANAGER");
//            userService.addRoleToUser("sameh","Manager");
//            userService.addRoleToUser("sofienne","ADMIN");
//            userService.addRoleToUser("sameh","ADMIN");
//
//
//        };
//}
}