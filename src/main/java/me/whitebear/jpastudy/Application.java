package me.whitebear.jpastudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "userAuditorAware") // auditorAware 의 빈이름을 넣어준다.
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}