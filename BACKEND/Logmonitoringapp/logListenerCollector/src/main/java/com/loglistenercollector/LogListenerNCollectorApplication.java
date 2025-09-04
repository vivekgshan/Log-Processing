package com.loglistenercollector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LogListenerNCollectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogListenerNCollectorApplication.class, args);

	}

}
