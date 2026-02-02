package com.example.point_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity

public class PointServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PointServiceApplication.class, args);
	}

}
