package com.example.point_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.example.point_service",
		"com.school.common"
})
public class PointServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(PointServiceApplication.class, args);
	}
}
