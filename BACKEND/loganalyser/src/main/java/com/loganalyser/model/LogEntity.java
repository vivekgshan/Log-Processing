package com.loganalyser.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "log_data") // ✅ lowercase for consistency with Postgres
public class LogEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String timestamp;
	private String logType;
	private String message;

	// Getters & Setters
	public Long getId() { return id; }

	public String getTimestamp() { return timestamp; }
	public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

	public String getLogType() { return logType; }
	public void setLogType(String logType) { this.logType = logType; }

	public String getMessage() { return message; }
	public void setMessage(String message) { this.message = message; }
}

