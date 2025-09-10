package com.loganalyser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "*")
public class LogAnalyserApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogAnalyserApplication.class, args);

	}

}
