package com.logdebugpersistor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LogDebugPersistorApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogDebugPersistorApplication.class, args);

	}

}
