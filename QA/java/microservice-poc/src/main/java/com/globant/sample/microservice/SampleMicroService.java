package com.globant.sample.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.hateoas.HateoasProperties;

/**
 * @author Juan Krzemien
 */
@SpringBootApplication(exclude = HateoasProperties.class)
public class SampleMicroService {

    public static void main(String[] args) {
        SpringApplication.run(SampleMicroService.class, args);
    }

}
