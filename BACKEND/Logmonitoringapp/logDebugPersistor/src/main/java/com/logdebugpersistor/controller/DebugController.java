package com.logdebugpersistor.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logdebugpersistor.model.LogEntity;
import com.logdebugpersistor.repository.LogRepository;

@RestController
@RequestMapping("/debug")
public class DebugController {

	private final LogRepository logRepository;

	public DebugController(LogRepository logRepository) {
		this.logRepository = logRepository;
	}

	@PostMapping
	public ResponseEntity<String> saveLog(@RequestBody LogEntity log) {
		logRepository.save(log);
		System.out.println("✅ Saved DEBUG log: " + log.getMessage());
		return ResponseEntity.ok("Debug log saved successfully");
	}

	@GetMapping
	public ResponseEntity<List<LogEntity>> getAllLogs() {
		return ResponseEntity.ok(logRepository.findAll());
	}
}
