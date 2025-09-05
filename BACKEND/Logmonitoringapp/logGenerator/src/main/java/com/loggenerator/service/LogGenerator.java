package com.loggenerator.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Component
public class LogGenerator {

	private final Random random = new Random();

	private final String[] logTypes = {"INFO", "DEBUG", "WARN", "ERROR"};
	private final String[] messages = {
			"User logged in successfully",
			"Database connection timeout",
			"Payment declined due to insufficient funds",
			"Cache refreshed successfully",
			"File upload completed",
			"Service unavailable, retrying...",
			"Request processed in 120ms",
			"Unauthorized access attempt detected"
	};

	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	private final String logDir = "config/logs";
	private final String logFile = logDir + "/app1.log";

	/**
	 * Generate logs every 30 seconds
	 */
	@Scheduled(fixedRate = 30000)
	public void generateLogBatch() {
		// Ensure log folder exists
		File folder = new File(logDir);
		if (!folder.exists()) {
			boolean created = folder.mkdirs();
			if (created) {
				System.out.println("✅ Created log directory: " + folder.getAbsolutePath());
			}
		}

		// Pick between 5 and 10 logs per interval
		int numberOfLogs = 5 + random.nextInt(6); // 5–10

		try (FileWriter writer = new FileWriter(logFile, false)) { // false = overwrite
			for (int i = 0; i < numberOfLogs; i++) {
				String timestamp = LocalDateTime.now().format(formatter);
				String logType = logTypes[random.nextInt(logTypes.length)];
				String message = messages[random.nextInt(messages.length)];

				String logLine = String.format("%s | %s | %s%n", timestamp, logType, message);
				System.out.print(logLine); // print to console
				writer.write(logLine);     // overwrite file with new batch
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
