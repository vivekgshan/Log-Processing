package com.logwarnpersistor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LogWarnPersistorApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogWarnPersistorApplication.class, args);

	}

}
