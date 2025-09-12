package com.loggenerator.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.loggenerator.dto.LogRequest;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

@Component
public class DBLogAppender {

	@Value("${logfoldername}")
	private String LOG_DIR;
	
	@Value("${logfilename}")
	private String LOG_FILE;
	
	
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10 MB
   // private final String baseFileName; 
    
    /*public DBLogAppender() {
    	String userHome = System.getProperty("user.home");
    	this.logDir= new File(userHome, LOG_DIR);
    	//this.baseFileName= LOG_FILE;
    	if (!logDir.exists()) {
            logDir.mkdirs(); // create folder if not exists
        }
    }*/

    public synchronized void saveMessage(LogRequest logRequest ) {
    	String userHome = System.getProperty("user.home");

        // Construct log folder path
        File logDir = new File(userHome, LOG_DIR);

        if (!logDir.exists()) {
            logDir.mkdirs(); // create folder if not exists
        }
        
        //File logFile = new File(logDir, LOG_FILE);
        File logFile = getActiveLogFile(logDir);
        try (FileWriter writer = new FileWriter(logFile, true)) {
            String timestamp = LocalDateTime.now().format(FORMATTER);
            String message= logRequest.getMessage();
            String level= logRequest.getLevel();
            writer.write(timestamp + " | " +level+" | " +message + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private File getActiveLogFile(File logDir) {
        File logFile = new File(logDir, LOG_FILE);

        // Roll over if file exceeds 10MB
        if (logFile.exists() && logFile.length() >= MAX_FILE_SIZE) {
            String rolledName = LOG_FILE.replace(".log", "")
                    + "-" + System.currentTimeMillis() + ".log";
            File rolledFile = new File(logDir, rolledName);
            logFile.renameTo(rolledFile); // rename old file
        }

        return new File(logDir, LOG_FILE);
    }
}
