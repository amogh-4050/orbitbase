package com.orbitbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OrbitbaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrbitbaseApplication.class, args);
    }

}