package com.loginfopersistor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LogInfoPersistorApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogInfoPersistorApplication.class, args);

	}

}
