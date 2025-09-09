package com.loganalyser.controller.dto;

public class LogData {

	private String timestamp;
	private String logType;
	private String message;
	public LogData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LogData(String timestamp, String logType, String message) {
		super();
		this.timestamp = timestamp;
		this.logType = logType;
		this.message = message;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
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