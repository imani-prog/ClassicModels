package com.classicmodels.classicmodels;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.classicmodels.classicmodels.Entities")
@EnableJpaRepositories("com.classicmodels.classicmodels.Repository")
public class ClassicmodelsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClassicmodelsApplication.class, args);
    }
}