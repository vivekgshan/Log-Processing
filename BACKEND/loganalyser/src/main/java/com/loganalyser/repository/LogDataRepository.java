package com.loganalyser.repository;

import com.loganalyser.model.LogData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface LogDataRepository extends JpaRepository<LogData, Long> {

	@Query("SELECT COUNT(l) FROM LogData l WHERE l.timestamp BETWEEN :start AND :end AND l.logtype = :logtype")
	Long countByLogtypeAndTimeBetween(@Param("start") LocalDateTime start,
	                                  @Param("end") LocalDateTime end,
	                                  @Param("logtype") String logtype);
	
	
	@Query("SELECT l.level, COUNT(l) FROM LogEntity l GROUP BY l.level")
	List<Object[]> countLogsByLevel();

	@Query("SELECT l FROM LogEntity l")
	List<LogData> getAllLogs();


}
