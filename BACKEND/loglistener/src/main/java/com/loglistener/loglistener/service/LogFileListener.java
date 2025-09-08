package com.loglistener.loglistener.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.loglistener.loglistener.request.LogEntry;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

@Component
public class LogFileListener {
	@Value("${logfoldername}")
	private String LOG_DIR;
	
	@Value("${log.collectorurl}")
	private String collectorUrl;
	
	RestTemplate restTemplate= new RestTemplate();
	String userHome = System.getProperty("user.home");
	private final File logDir = new File(userHome+"/log");
	private final Map<String, Long> filePointerMap = new HashMap<>();
	

	@Scheduled(fixedDelay = 10000) // check every 10 seconds
	public void watchLogs() {
		if (!logDir.exists() || !logDir.isDirectory()) {
			System.out.println("Log directory not found: " + logDir.getAbsolutePath());
			return;
		}

		File[] files = logDir.listFiles((dir, name) -> name.endsWith(".log"));
		if (files == null) return;

		for (File file : files) {
			try {
				if (!filePointerMap.containsKey(file.getName())) {
					System.out.println("Now listening to new file: " + file.getName());
					filePointerMap.put(file.getName(), -1L);
				}

				// delegate actual reading to collector
				collectLogs(file, filePointerMap);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void collectLogs(File file, Map<String, Long> filePointerMap) throws IOException {
		String fileName = file.getName();

		long lastPointer = filePointerMap.getOrDefault(fileName, -1L);

		try (RandomAccessFile reader = new RandomAccessFile(file, "r")) {
			long fileLength = reader.length();

			if (lastPointer == -1L) {
				System.out.println(" New file detected: " + fileName + " → sending full content once");
				lastPointer = 0L;
			}

			if (fileLength < lastPointer) {
				System.out.println("[" + fileName + "] File was reset. Re-reading from start.");
				lastPointer = 0L;
			}

			reader.seek(lastPointer);

			if (fileLength > lastPointer) {
				System.out.println(" Changes detected in file: " + fileName);

				String line;
				while ((line = reader.readLine()) != null) {
					String logLine = "[" + fileName + "] " + line;
					System.out.println(logLine);

					// Parse and send to respective microservice
					LogEntry entry = parseLog(line);
					if (entry != null) {
						sendLogToCollector(entry);
					}
				}

				filePointerMap.put(fileName, reader.getFilePointer());
			}
		}
	}

	// Parse log line into structured entry
	private LogEntry parseLog(String logLine) {
		try {
			// Example format: "2025-09-03 16:36:23 | WARN | Some message"
			String[] parts = logLine.split("\\|", 3); // split into 3 parts

			if (parts.length < 3) {
				throw new IllegalArgumentException("Invalid log line: " + logLine);
			}

			String timestamp = parts[0].trim();       // "2025-09-03 16:36:23"
			String logType   = parts[1].trim().toUpperCase(); // "WARN" (normalized to uppercase)
			String message   = parts[2].trim();       // "Some message"

			return new LogEntry(timestamp, logType, message);

		} catch (Exception e) {
			System.err.println("❌ Failed to parse log line: " + logLine);
			return null;
		}
	}


	private void sendLogToCollector(LogEntry entry) {
		
		try {
			
			ResponseEntity<String> response = restTemplate.postForEntity(collectorUrl, entry, String.class);
			System.out.println("Log posted: " + response.getBody());
		} catch (Exception e) {
			System.err.println("Failed to call /logs: " + e.getMessage());
		}
		
	}

	// DTO for logs
	/*static class LogEntry {
		private String timestamp;
		private String logType;
		private String message;

		public LogEntry(String timestamp, String logType, String message) {
			this.timestamp = timestamp;
			this.logType = logType;
			this.message = message;
		}

		public String getTimestamp() { return timestamp; }
		public String getLogType() { return logType; }
		public String getMessage() { return message; }
	}*/

}