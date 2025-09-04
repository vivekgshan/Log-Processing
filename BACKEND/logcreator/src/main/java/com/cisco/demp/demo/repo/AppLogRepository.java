package com.cisco.demp.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cisco.demp.demo.model.LogEntity;

public interface AppLogRepository extends JpaRepository<LogEntity, Long>{

}
