package com.logcollector.logcollector.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.logcollector.logcollector.request.LogEntry;
import com.logcollector.logcollector.service.LogFileCollector;

@RestController
public class LogCollectorController {
	

	private LogFileCollector logFileCollector;
	public LogCollectorController(LogFileCollector logFileCollector) {
		this.logFileCollector= logFileCollector;
	}
	
	@PostMapping("/collector")
	public void collectLog(@RequestBody LogEntry logEntry ) {
		logFileCollector.sendLogToPersitor(logEntry);
	}
}
