package com.loganalyser.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "log_data") // ✅ lowercase for consistency with Postgres
public class LogEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDateTime  timestamp;
	private String logType;
	private String message;

	// Getters & Setters
	public Long getId() { return id; }


	public LocalDateTime getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}


	public String getLogType() { return logType; }
	public void setLogType(String logType) { this.logType = logType; }

	public String getMessage() { return message; }
	public void setMessage(String message) { this.message = message; }


	@Override
	public String toString() {
		return "LogEntity [id=" + id + ", timestamp=" + timestamp + ", logType=" + logType + ", message=" + message
				+ "]";
	}
	
	
}
