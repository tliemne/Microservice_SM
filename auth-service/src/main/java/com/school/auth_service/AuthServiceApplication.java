package com.school.auth_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

<<<<<<< HEAD
@SpringBootApplication
@ComponentScan(basePackages = {"com.school.auth_service", "com.school.common_library"})
@EnableFeignClients
=======
@SpringBootApplication(
		scanBasePackages = {
				"com.school.auth_service",
				"com.school.common_library"
		},
		exclude = {
				DataSourceAutoConfiguration.class,
				DataSourceTransactionManagerAutoConfiguration.class,
				HibernateJpaAutoConfiguration.class
		}
)
>>>>>>> 75ddd60e06d84e8b139f6bda6a5ad24477274b51
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

}
