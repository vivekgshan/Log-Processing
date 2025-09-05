package com.loganalyser.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "log_data")
public class LogData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "log_type") // maps to DB column log_type
	private String logtype;

	@Column(name = "message")
	private String message;

	@Column(name = "log_timestamp")
	private LocalDateTime timestamp;


	// getters and setters
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public String getLogtype() { return logtype; }
	public void setLogtype(String logtype) { this.logtype = logtype; }

	public String getMessage() { return message; }
	public void setMessage(String message) { this.message = message; }

	public LocalDateTime getTimestamp() { return timestamp; }
	public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
