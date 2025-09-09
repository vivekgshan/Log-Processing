package com.loganalyser.repository;

import com.loganalyser.model.LogData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface LogDataRepository extends JpaRepository<LogData, Long> {

	@Repository

public interface LogDataRepository extends JpaRepository<LogData, Long> {

    long countByLogtypeAndTimestampBetween(String logtype, LocalDateTime start, LocalDateTime end);

}
	@Query("SELECT l.logtype, COUNT(l) FROM LogData l GROUP BY l.logtype")
	List<Object[]> countLogsByLevel();

	@Query("SELECT l FROM LogData l")
	List<LogData> getAllLogs();


}
