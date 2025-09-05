package com.loggenerator.controller;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loggenerator.dto.LogRequest;
import com.loggenerator.service.DBLogAppender;


@RestController
@RequestMapping("/logs")
public class LogController {
	
 
	@Autowired
	DBLogAppender dBLogAppender;
	
    //private static final String[] logLevels = {"INFO", "WARN", "ERROR", "DEBUG"};
	
	private  final Logger log = LoggerFactory.getLogger(LogController.class);
	 @GetMapping("/test-log")
	    public String testLog() {
	        log.info("This is an INFO log");
	        log.warn("This is a WARN log");
	        log.error("This is an ERROR log");
	        return "Logs written! Check DB.";
	    }
	 
	 @PostMapping
	 public String createLog(@RequestBody LogRequest logRequest) {
		 dBLogAppender.saveMessage(logRequest);
		 return "Log saved to DB!";
	 }
}