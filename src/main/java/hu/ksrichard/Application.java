package hu.ksrichard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Static entry point for Spring Boot application
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = {"hu.ksrichard.repo"})
public class Application {

    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }

}
