package com.pidev.esprit.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.*;

@Configuration
public class SpringDocConfig {
    @Bean
    public GroupedOpenApi productPublicApi() {
        return GroupedOpenApi.builder()
                .group("Only Product Management API")
                .pathsToMatch("/contrat/**")
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