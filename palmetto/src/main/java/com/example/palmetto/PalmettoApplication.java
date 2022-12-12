package com.example.palmetto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class PalmettoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PalmettoApplication.class, args);
    }

}
