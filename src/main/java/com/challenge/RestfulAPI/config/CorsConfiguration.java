package com.challenge.RestfulAPI.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author abdessamadM on 23/06/2020
 */
@Configuration
public class CorsConfiguration {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
                @Override
                public void addCorsMappings(CorsRegistry registry) { registry.addMapping("/**")
                        .allowedMethods("POST", "GET", "DELETE", "PUT");
                }
        };
    }
}
