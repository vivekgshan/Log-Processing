package com.loglistener.loglistener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LoglistenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoglistenerApplication.class, args);
	}

}
