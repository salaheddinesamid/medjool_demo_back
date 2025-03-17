package com.example.medjool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MedjoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedjoolApplication.class, args);
	}

}
