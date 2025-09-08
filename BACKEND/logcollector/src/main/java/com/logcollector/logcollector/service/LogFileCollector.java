package com.logcollector.logcollector.service;


import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.logcollector.logcollector.request.LogEntry;

@Component
public class LogFileCollector {

	private final RestTemplate restTemplate = new RestTemplate();

	@Value("${infourl}")
	private String infoUrl ;
	
	@Value("${errorurl}")
	private String errorUrl;
	
	@Value("${warnurl}")
	private String warnUrl;
	
	@Value("${debugurl}")
	private String debugUrl ;

/*	public void collectLogs(File file, Map<String, Long> filePointerMap) throws IOException {
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
						sendLogToMicroservice(entry);
					}
				}

				filePointerMap.put(fileName, reader.getFilePointer());
			}
		}
	}*/

	// Parse log line into structured entry
	/*private LogEntry parseLog(String logLine) {
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
	}*/


	// Send to correct microservice based on logType
	public void sendLogToPersitor(LogEntry entry) {
		String url = null;

		switch ( entry.getLogType().trim().toUpperCase()){

			case "INFO":
				url = infoUrl;
				break;
			case "ERROR":
				url = errorUrl;
				break;
			case "WARN":
				url = warnUrl;
				break;
			case "DEBUG":
				url = debugUrl;
				break;
		}

		if (url != null) {
			try {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);

				HttpEntity<LogEntry> request = new HttpEntity<>(entry, headers);

				ResponseEntity<String> response =
						restTemplate.postForEntity(url, request, String.class);

				System.out.println("📤 Sent " + entry.getLogType() +
						" log → " + url + " | Response: " + response.getBody());
			} catch (Exception e) {
				System.err.println("⚠️ Failed to send log to microservice: " + e.getMessage());
			}
		}
	}

	// DTO for logs
/*	static class LogEntry {
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