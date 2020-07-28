package com.challenge.RestfulAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RestfulApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestfulApiApplication.class, args);
		System.out.println("The BackEnd App is running");
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
