package com.loginfopersistor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.loginfopersistor.DTO.LogEntry;
import com.loginfopersistor.model.LogEntity;
import com.loginfopersistor.repository.LogRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@RestController
@RequestMapping("/error")
public class ErrorController {

	private final LogRepository logRepository;

	public ErrorController(LogRepository logRepository) {
		this.logRepository = logRepository;
	}
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	@PostMapping
	public ResponseEntity<String> saveLog(@RequestBody LogEntry log) {
		LogEntity le= new LogEntity();
		le.setLogType(log.getLogType());
		le.setMessage(log.getMessage());
		le.setTimestamp(LocalDateTime.parse(log.getTimestamp(), FORMATTER));
		logRepository.save(le);

		System.out.println("✅ Saved ERROR log: " + log.getMessage());
		return ResponseEntity.ok("Error log saved successfully");
	}

	@GetMapping
	public ResponseEntity<List<LogEntity>> getAllLogs() {
		return ResponseEntity.ok(logRepository.findAll());
	}
}
