package com.loginfopersistor.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loginfopersistor.DTO.LogEntry;
import com.loginfopersistor.model.LogEntity;
import com.loginfopersistor.repository.LogRepository;

@RestController
@RequestMapping("/info")
public class InfoController {

	private final LogRepository logRepository;

	public InfoController(LogRepository logRepository) {
		this.logRepository = logRepository;
	}
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	@PostMapping
	public ResponseEntity<String> saveLog(@RequestBody LogEntry log) {
		LogEntity le= new LogEntity();
		le.setLogType(log.getLogType());
		le.setMessage(log.getMessage());
		le.setTimestamp(log.getTimestamp());
		logRepository.save(le);
		System.out.println("✅ Saved INFO log: " + log.getMessage());
		return ResponseEntity.ok("Info log saved successfully");
	}

	@GetMapping
	public ResponseEntity<List<LogEntity>> getAllLogs() {
		return ResponseEntity.ok(logRepository.findAll());
	}
}
