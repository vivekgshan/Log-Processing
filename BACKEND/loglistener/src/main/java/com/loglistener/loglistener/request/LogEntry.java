package com.loglistener.loglistener.request;

public class LogEntry {

	private String timestamp;
	private String logType;
	private String message;

	public LogEntry(String timestamp, String logType, String message) {
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

	@Override
	public String toString() {
		return "LogEntry [timestamp=" + timestamp + ", logType=" + logType + ", message=" + message + "]";
	}


}
