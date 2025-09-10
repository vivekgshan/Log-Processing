package com.loganalyser.service;

import org.springframework.stereotype.Service;

import com.loganalyser.controller.dto.LogData;
import com.loganalyser.model.LogEntity;
import com.loganalyser.repository.LogDataRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.sql.Timestamp;

@Service
public class LogDataService {
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private static final DateTimeFormatter FORMATTER_WITH_T =
            DateTimeFormatter.ISO_LOCAL_DATE_TIME; // yyyy-MM-dd'T'HH:mm:ss

	LogDataRepository repository;

	public LogDataService(LogDataRepository repository) {
		this.repository = repository;
	}

	public Map<String, Long> getLogCountsByLevel() {
        List<Object[]> results = repository.countLogsByLevel();
        Map<String, Long> counts = new HashMap<>();
        for (Object[] row : results) {
            counts.put((String) row[0], (Long) row[1]);
        }
        return counts;
    }
	
	public List<Map<String, Object>> getLogCountsPer5Minutes() {
	    List<LogEntity> logs = repository.getAllLogs();

	    Map<LocalDateTime, Map<String, Long>> grouped = new TreeMap<LocalDateTime, Map<String,Long>>();

	    for (LogEntity log : logs) {
	        String timest = log.getTimestamp();
	    	
	    	//LocalDateTime ts = LocalDateTime.parse(timest, FORMATTER);
	    	LocalDateTime ts = parseFlexible(timest);
	        int minute = (ts.getMinute() / 5) * 5;
	        LocalDateTime bucket = ts.withMinute(minute).withSecond(0).withNano(0);

	        grouped.putIfAbsent(bucket, new HashMap<>());
	        Map<String, Long> levelCounts = grouped.get(bucket);

	        levelCounts.put(log.getLogType(),
	            levelCounts.getOrDefault(log.getLogType(), 0L) + 1);
	    }

	    List<Map<String, Object>> output = new ArrayList<>();
	    for (var entry : grouped.entrySet()) {
	        Map<String, Object> map = new LinkedHashMap<>();
	        map.put("timestamp", entry.getKey().toString());
	        map.put("ERROR", entry.getValue().getOrDefault("ERROR", 0L));
	        map.put("WARN", entry.getValue().getOrDefault("WARN", 0L));
	        map.put("INFO", entry.getValue().getOrDefault("INFO", 0L));
	        map.put("DEBUG", entry.getValue().getOrDefault("DEBUG", 0L));
	        output.add(map);
	    }
	    return output;
	}

	public List<LogData> findLogsOrderedByTimestampDesc(){
		List<LogEntity> logEntityList=repository.logs();
		List<LogData> ldList= new ArrayList<LogData>();
		
		for(LogEntity le:logEntityList) {
			LogData ld= new LogData();
			ld.setTimestamp(le.getTimestamp());
			ld.setLogType(le.getLogType());
			ld.setMessage(le.getMessage());
			ldList.add(ld);
		}
		return ldList;
	}
	
    public static LocalDateTime parseFlexible(String dateTime) {
        try {
            return LocalDateTime.parse(dateTime, FORMATTER_WITH_T);
        } catch (Exception e) {
            return LocalDateTime.parse(dateTime, FORMATTER);
        }
    }
}
