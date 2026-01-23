package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.example.entities")                // <- aquí indicamos donde están las entidades
@EnableJpaRepositories("com.example.repositories") // <- aquí indicamos donde están los repositorios
public class FormulariosEncadenadosBbddApplication {

	public static void main(String[] args) {
		SpringApplication.run(FormulariosEncadenadosBbddApplication.class, args);
	}

}
