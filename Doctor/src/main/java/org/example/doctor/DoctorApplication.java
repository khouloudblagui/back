package org.example.doctor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class DoctorApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoctorApplication.class, args);
    }

}