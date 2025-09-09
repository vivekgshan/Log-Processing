package com.loganalyser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.loganalyser.dto.LogData;
import com.loganalyser.model.LogEntity;
import com.loganalyser.service.LogDataService;

@RestController
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
		List<LogEntity> logEntityList=logExportService.findLogsOrderedByTimestampDesc();
		List<LogData> ldList= new ArrayList<LogData>();
		LogData ld= new LogData();
		for(LogEntity le:logEntityList) {
			ld.setTimestamp(le.getTimestamp());
			ld.setLogType(le.getLogType());
			ld.setMessage(le.getMessage());
			ldList.add(ld);
		}
		
        return ldList;
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







