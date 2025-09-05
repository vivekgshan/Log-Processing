package com.loglistenercollector.service;

import com.loglistenercollector.service.LogCollector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class LogFileListener {
	@Value("${logfoldername}")
	private String LOG_DIR;
	
	@Value("${logfilename}")
	private String LOG_FILE;
	String userHome = System.getProperty("user.home");

	private final File logDir = new File(userHome+"/log");
	private final Map<String, Long> filePointerMap = new HashMap<>();
	
	@Autowired
	private LogCollector logCollector ;// = new LogCollector();

	@Scheduled(fixedDelay = 10000) // check every 10 seconds
	public void watchLogs() {
		if (!logDir.exists() || !logDir.isDirectory()) {
			System.out.println("Log directory not found: " + logDir.getAbsolutePath());
			return;
		}

		File[] files = logDir.listFiles((dir, name) -> name.endsWith(".txt"));
		if (files == null) return;

		for (File file : files) {
			try {
				if (!filePointerMap.containsKey(file.getName())) {
					System.out.println("Now listening to new file: " + file.getName());
					filePointerMap.put(file.getName(), -1L);
				}

				// delegate actual reading to collector
				logCollector.collectLogs(file, filePointerMap);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
