package com.loganalyser.repository;

import com.loganalyser.model.LogData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface LogDataRepository extends JpaRepository<LogData, Long> {

	@Query("SELECT COUNT(l) FROM LogData l WHERE l.timestamp BETWEEN :start AND :end AND l.logtype = :logtype")
	Long countByLogtypeAndTimeBetween(@Param("start") LocalDateTime start,
	                                  @Param("end") LocalDateTime end,
	                                  @Param("logtype") String logtype);


}
