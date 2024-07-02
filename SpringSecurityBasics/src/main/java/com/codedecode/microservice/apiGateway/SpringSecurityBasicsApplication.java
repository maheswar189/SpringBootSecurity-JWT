package com.codedecode.microservice.apiGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringSecurityBasicsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityBasicsApplication.class, args);
	}

}
