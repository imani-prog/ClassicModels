package com.classicmodels.classicmodels;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;

//@SpringBootApplication(scanBasePackages = "com.classicmodels.classicmodels")
//@EntityScan(basePackages = "com.classicmodels.classicmodels.entities")
//@EnableJpaRepositories(basePackages = "com.classicmodels.classicmodels.repository")



@SpringBootApplication
@EntityScan("com.classicmodels.classicmodels.entities")
//@EnableJpaRepositories("com.classicmodels.classicmodels.repository")
public class ClassicmodelsApplication {


    public static void main(String[] args) {
        SpringApplication.run(ClassicmodelsApplication.class, args);
    }
   @GetMapping
    public String helloWorld(){
        return "Hello World Spring Boot";
    }
}