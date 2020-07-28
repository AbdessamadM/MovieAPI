package com.challenge.RestfulAPI.config;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * @Author abdessamadM on 23/06/2020
 */

@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

    // Swagger configuration with some filters and custom information
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.challenge.RestfulAPI.controllers.v1"))
                .paths(paths())
                .build()
                .pathMapping("/")
                .apiInfo(metadata());
    }

    private Predicate<String> paths() {
        return regex("/api/.*");
    }

    private ApiInfo metadata() {
        Contact contact = new Contact("Abdessamad Mouzakki", "", "abdessamad.mouzakki@gmail.com");
        return new ApiInfo(
                "RestFUL API",
                "Building a simple REST API - a basic movie database interacting with external API.",
                "1.0",
                "Terms of service: .",
                contact,
                "Apache Licence Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>()
        );
    }
}
