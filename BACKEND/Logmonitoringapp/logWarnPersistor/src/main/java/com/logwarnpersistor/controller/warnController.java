package com.logwarnpersistor.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.logwarnpersistor.model.LogEntity;
import com.logwarnpersistor.repository.LogRepository;

@RestController
@RequestMapping("/warn")
public class warnController {

	private final LogRepository logRepository;

	public warnController(LogRepository logRepository) {
		this.logRepository = logRepository;
	}

	@PostMapping
	public ResponseEntity<String> saveLog(@RequestBody LogEntity log) {
		logRepository.save(log);
		System.out.println("✅ Saved WARN log: " + log.getMessage());
		return ResponseEntity.ok("Warn log saved successfully");
	}

	@GetMapping
	public ResponseEntity<List<LogEntity>> getAllLogs() {
		return ResponseEntity.ok(logRepository.findAll());
	}
}
