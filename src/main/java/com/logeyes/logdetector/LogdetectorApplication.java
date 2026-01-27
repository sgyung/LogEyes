package com.logeyes.logdetector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class LogdetectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogdetectorApplication.class, args);
	}

}
