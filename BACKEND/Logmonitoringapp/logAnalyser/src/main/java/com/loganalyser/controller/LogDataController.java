package com.loganalyser.controller;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.loganalyser.service.LogDataService;

@RestController
@RequestMapping("/logs")
 public class LogDataController {

		private final LogDataService service;

		public LogDataController(LogDataService service) {
			this.service = service;
		}
	@GetMapping("/count")
	public Long getLogCount(
			@RequestParam(name = "start") String start,
			@RequestParam(name = "end") String end,
			@RequestParam(name = "logtype") String logtype
	) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

			LocalDateTime startTime = LocalDateTime.parse(start.trim(), formatter);
			LocalDateTime endTime = LocalDateTime.parse(end.trim(), formatter);

			return service.getLogCountBetween(startTime, endTime, logtype.trim());
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid input. Use format: yyyy-MM-dd HH:mm:ss", e);
		}
	}

}







