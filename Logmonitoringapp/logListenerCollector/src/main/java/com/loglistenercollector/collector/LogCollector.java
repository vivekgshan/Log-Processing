package com.loglistenercollector.collector;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class LogCollector {

	private final RestTemplate restTemplate = new RestTemplate();
	private final String logServiceUrl = "http://localhost:8081/app/logs/messages";

	public void collectLogs(File file, Map<String, Long> filePointerMap) throws IOException {
		String fileName = file.getName();

		// -1 means first time seeing this file
		long lastPointer = filePointerMap.getOrDefault(fileName, -1L);

		try (RandomAccessFile reader = new RandomAccessFile(file, "r")) {
			long fileLength = reader.length();

			// Case 1: New file
			if (lastPointer == -1L) {
				System.out.println(" New file detected: " + fileName + " → sending full content once");
				lastPointer = 0L;
			}

			// Case 2: File reset/truncated
			if (fileLength < lastPointer) {
				System.out.println("[" + fileName + "] File was reset. Re-reading from start.");
				lastPointer = 0L;
			}

			// Move to last read position
			reader.seek(lastPointer);

			// Case 3: File grew → read new lines
			if (fileLength > lastPointer) {
				System.out.println(" Changes detected in file: " + fileName);

				String line;
				while ((line = reader.readLine()) != null) {
					String logLine = "[" + fileName + "] " + line;
					System.out.println(logLine);
					sendLogToApi(logLine);
				}

				// update pointer
				filePointerMap.put(fileName, reader.getFilePointer());
			}
		}
	}

	private void sendLogToApi(String logLine) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.TEXT_PLAIN);

			HttpEntity<String> request = new HttpEntity<>(logLine, headers);

			ResponseEntity<String> response =
					restTemplate.postForEntity(logServiceUrl, request, String.class);

			System.out.println("📤 Sent log to API: " + logLine + " | Response: " + response.getBody());
		} catch (Exception e) {
			System.err.println("❌ Failed to send log to API: " + e.getMessage());
		}
	}
}
