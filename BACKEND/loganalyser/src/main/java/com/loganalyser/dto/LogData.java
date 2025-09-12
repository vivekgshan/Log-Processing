package com.loganalyser.dto;

import java.time.LocalDateTime;

public class LogData {

	private LocalDateTime timestamp;
	private String logType;
	private String message;
	public LogData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LogData(LocalDateTime timestamp, String logType, String message) {
		super();
		this.timestamp = timestamp;
		this.logType = logType;
		this.message = message;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public String getLogType() {
		return logType;
	}
	public void setLogType(String logType) {
		this.logType = logType;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}