package com.loginfopersistor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loginfopersistor.model.LogEntity;

public interface LogRepository extends JpaRepository<LogEntity, Long> {
}
