package com.loganalyser.service;

import org.springframework.stereotype.Service;
import com.loganalyser.repository.LogDataRepository;

import java.time.LocalDateTime;
import java.sql.Timestamp;

@Service
public class LogDataService {

	private final LogDataRepository repository;

	public LogDataService(LogDataRepository repository) {
		this.repository = repository;
	}

	public Long getLogCountBetween(LocalDateTime start, LocalDateTime end, String logtype) {
		if (start == null || end == null || logtype == null || logtype.isBlank()) {
			throw new IllegalArgumentException("Start, end, and logtype must not be null/empty");
		}

		// Pass LocalDateTime directly
		return repository.countByLogtypeAndTimeBetween(start, end, logtype);
	}

}
