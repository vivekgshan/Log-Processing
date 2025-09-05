package com.loggenerator.scheduler;

import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.loggenerator.dto.LogRequest;


@Component
public class LogScheduler {
	@Value("${external.service.url}")
	private String url;
	private final RestTemplate restTemplate = new RestTemplate();
	
	private static final Random random = new Random();
	 
    private static final String[] logMessages = {
            "User login successful",
            "User failed authentication",
            "Database connection timeout",
            "Payment processed successfully",
            "Payment declined due to insufficient funds",
            "Cache refreshed successfully",
            "External API call failed",
            "File upload completed"
    };

    private static final String[] logLevels = {"INFO", "WARN", "ERROR", "DEBUG"};
	
	@Scheduled(fixedRate = 30000)
	public void generateLog() {
		
		//String url= "http://localhost:9096/logs";
		String message = logMessages[random.nextInt(logMessages.length)];
        String level = logLevels[random.nextInt(logLevels.length)];
		LogRequest logRequest= new LogRequest();
		logRequest.setLevel(level);
		logRequest.setMessage(message);
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(url, logRequest, String.class);
			System.out.println("Log posted: " + response.getBody());
		} catch (Exception e) {
			System.err.println("Failed to call /logs: " + e.getMessage());
		}
	}
}

