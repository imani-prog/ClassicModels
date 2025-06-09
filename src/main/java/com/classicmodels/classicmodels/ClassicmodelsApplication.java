package com.classicmodels.classicmodels;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.Connection;


@SpringBootApplication
public class ClassicmodelsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClassicmodelsApplication.class, args);
    }

    @Bean
    public CommandLineRunner testConnection(DataSource dataSource) {
        return args -> {
            try (Connection conn = dataSource.getConnection()) {
                System.out.println("CONNECTION SUCCESSFUL!");
                System.out.println("Catalog: " + conn.getCatalog());
            } catch (Exception e) {
                System.out.println("CONNECTION FAILED!");
                e.printStackTrace();
            }
        };
    }

}