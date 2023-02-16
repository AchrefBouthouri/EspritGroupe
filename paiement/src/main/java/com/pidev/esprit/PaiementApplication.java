package com.pidev.esprit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PaiementApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaiementApplication.class, args);
    }
}