package com.loginfopersistor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LogErrorPersistorApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogErrorPersistorApplication.class, args);

	}

}
