package com.smartcontactmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmartContactManagerApplication {

	public static void main(String[] args) {

		// Dotenv dotenv = Dotenv.load();
		// System.out.println("DB_URL: " + System.getenv("DB_URL"));

		SpringApplication.run(SmartContactManagerApplication.class, args);
	}

}