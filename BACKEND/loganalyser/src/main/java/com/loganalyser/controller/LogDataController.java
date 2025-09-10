package com.loganalyser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.loganalyser.controller.dto.LogData;
import com.loganalyser.model.LogEntity;
import com.loganalyser.service.LogDataService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/logs")
 public class LogDataController {

		private final LogDataService service;

		public LogDataController(LogDataService service) {
			this.service = service;
		}
	/*@GetMapping("/count")
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
	}*/
	
	@Autowired
	LogDataService logExportService;
	
	@GetMapping
    public List<LogData> getAllLogs() {
		
		
        return logExportService.findLogsOrderedByTimestampDesc();
    }
	 
	@GetMapping("/count")
	public Map<String, Long> countLogsByLevel(){
		return logExportService.getLogCountsByLevel();
	}
	
	@GetMapping("/linegraph")
	public List<Map<String, Object>> getLogCountsPer5Minutes(){
		return logExportService.getLogCountsPer5Minutes();
	}

}







