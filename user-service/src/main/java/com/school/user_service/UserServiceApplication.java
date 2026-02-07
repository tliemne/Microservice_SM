package com.school.user_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@ComponentScan(basePackages = {"com.school.user_service", "com.school.common_library"})
public class UserServiceApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context =
				SpringApplication.run(UserServiceApplication.class, args);

		// Lấy PasswordEncoder từ Spring
		PasswordEncoder encoder = context.getBean(PasswordEncoder.class);

		// 👉 mật khẩu bạn MUỐN TẠO
		String rawPassword = "123456";

		// 👉 mã hóa
		String encodedPassword = encoder.encode(rawPassword);

		// 👉 in ra cho bạn dùng
		System.out.println("=================================");
		System.out.println("MẬT KHẨU GỐC : " + rawPassword);
		System.out.println("HASH LƯU DB : " + encodedPassword);
		System.out.println("=================================");
	}

}
