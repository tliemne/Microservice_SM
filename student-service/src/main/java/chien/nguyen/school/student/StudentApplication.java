package chien.nguyen.school.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication(scanBasePackages = {
        "chien.nguyen.school.student",
        "com.school.common_library"
})
@EnableFeignClients(basePackages = "chien.nguyen.school.student.repository.client")
public class StudentApplication {
  public static void main(String[] args) { SpringApplication.run(StudentApplication.class, args); }

//  @Bean
//  CommandLineRunner generatePassword() {
//    return args -> {
//      BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//
//      String rawPassword = "123456";
//      String encoded = encoder.encode(rawPassword);
//
//      System.out.println("Raw password: " + rawPassword);
//      System.out.println("Encoded password: " + encoded);
//    };
//  }
}
