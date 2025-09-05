package com.loggenerator.dto;

public class LogRequest {

	private String level;
    private String message;
    
    
	public LogRequest() {
		super();
		// TODO Auto-generated constructor stub
	}


	public LogRequest(String level, String message) {
		super();
		this.level = level;
		this.message = message;
	}


	public String getLevel() {
		return level;
	}


	public void setLevel(String level) {
		this.level = level;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	@Override
	public String toString() {
		return "LogRequest [level=" + level + ", message=" + message + "]";
	}

}
