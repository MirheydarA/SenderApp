package org.example.senderapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableFeignClients
public class SenderAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SenderAppApplication.class, args);
    }

}
