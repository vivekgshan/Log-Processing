package com.loganalyser.repository;

import com.loganalyser.model.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface LogDataRepository extends JpaRepository<LogEntity, Long> {

	/*@Query("SELECT COUNT(l) FROM LogData l WHERE l.timestamp BETWEEN :start AND :end AND l.logtype = :logtype")
	Long countByLogtypeAndTimeBetween(@Param("start") LocalDateTime start,
	                                  @Param("end") LocalDateTime end,
	                                  @Param("logtype") String logtype);*/
	
	

	@Query("SELECT l.logType, COUNT(l) FROM LogEntity l GROUP BY l.logType")
	List<Object[]> countLogsByLevel();

	@Query("SELECT l FROM LogEntity l")
	public List<LogEntity> getAllLogs();
	
	@Query("SELECT l FROM LogEntity l ORDER BY l.timestamp DESC")
	public List<LogEntity> logs();


}
