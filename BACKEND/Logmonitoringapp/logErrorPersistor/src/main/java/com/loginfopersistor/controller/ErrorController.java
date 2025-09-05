package com.loginfopersistor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.loginfopersistor.model.LogEntity;
import com.loginfopersistor.repository.LogRepository;

@RestController
@RequestMapping("/error")
public class ErrorController {

	private final LogRepository logRepository;

	public ErrorController(LogRepository logRepository) {
		this.logRepository = logRepository;
	}

	@PostMapping
	public ResponseEntity<String> saveLog(@RequestBody LogEntity log) {
		logRepository.save(log);
		System.out.println("✅ Saved ERROR log: " + log.getMessage());
		return ResponseEntity.ok("Error log saved successfully");
	}

	@GetMapping
	public ResponseEntity<List<LogEntity>> getAllLogs() {
		return ResponseEntity.ok(logRepository.findAll());
	}
}
