package com.logdebugpersistor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.logdebugpersistor.model.LogEntity;

public interface LogRepository extends JpaRepository<LogEntity, Long> {
}
