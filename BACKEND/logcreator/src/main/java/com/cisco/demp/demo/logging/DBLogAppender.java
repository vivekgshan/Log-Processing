package com.cisco.demp.demo.logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cisco.demp.demo.dto.LogRequest;
import com.cisco.demp.demo.model.LogEntity;
import com.cisco.demp.demo.repo.AppLogRepository;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

@Component
public class DBLogAppender {

	private static AppLogRepository staticRepository;

    @Autowired
    public void setRepository(AppLogRepository repo) {
        DBLogAppender.staticRepository = repo;
    }

    /*public void append(ILoggingEvent event) {
        if (staticRepository == null) return;
        try {
	        LogEntity log = new LogEntity();
	        log.setTimestamp(LocalDateTime.now());
	        log.setLevel(event.getLevel().toString());
	        log.setMessage(event.getFormattedMessage());
	        staticRepository.save(log);
        } catch (Exception e) {
        	System.err.println("Failed to persist log: " + e.getMessage());
		}
    }*/
    
    private static final String LOG_DIR = "log"; // folder at runtime
    private static final String LOG_FILE = "app-logs.txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public synchronized void saveMessage(LogRequest logRequest ) {
    	File logDir = new File(LOG_DIR);
        if (!logDir.exists()) {
            logDir.mkdirs(); // create folder if not exists
        }

        File logFile = new File(logDir, LOG_FILE);
        try (FileWriter writer = new FileWriter(logFile, true)) {
            String timestamp = LocalDateTime.now().format(FORMATTER);
            String message= logRequest.getMessage();
            String level= logRequest.getLevel();
            writer.write(timestamp + " | " +level+" | " +message + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
