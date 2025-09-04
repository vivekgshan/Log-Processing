package com.loginfopersistor.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loginfopersistor.model.LogEntity;
import com.loginfopersistor.repository.LogRepository;

@RestController
@RequestMapping("/info")
public class InfoController {

	private final LogRepository logRepository;

	public InfoController(LogRepository logRepository) {
		this.logRepository = logRepository;
	}

	@PostMapping
	public ResponseEntity<String> saveLog(@RequestBody LogEntity log) {
		logRepository.save(log);
		System.out.println("✅ Saved INFO log: " + log.getMessage());
		return ResponseEntity.ok("Info log saved successfully");
	}

	@GetMapping
	public ResponseEntity<List<LogEntity>> getAllLogs() {
		return ResponseEntity.ok(logRepository.findAll());
	}
}
