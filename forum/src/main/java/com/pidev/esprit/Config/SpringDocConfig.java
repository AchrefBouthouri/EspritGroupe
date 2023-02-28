package com.pidev.esprit.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI().info(infoAPI());
    }

    public Info infoAPI() {
        return new Info().title("Digital Dynamics").description("Digital Dynamics").version("1.0").contact(contactAPI());
    }

    public Contact contactAPI() {
        Contact contact = new Contact().name("Digital Dynamics").email("Digital Dynamics@esprit.tn").url("https://www.linkedin.com/in/Digital_Dynamics/");
        return contact;
    }
    @Bean
    public GroupedOpenApi productPublicApi() {
        return GroupedOpenApi.builder()
                .group("Only Product Management API")
                .pathsToMatch("/Digital Dynamics/**")
                .pathsToExclude("**")
                .build();
    }
    @Bean
    public GroupedOpenApi All() {
        return GroupedOpenApi.builder()
                .group("ALL API")
                .pathsToMatch("/**")
                .build();
    }
}
