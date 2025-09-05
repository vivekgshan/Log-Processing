package com.logwarnpersistor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.logwarnpersistor.model.LogEntity;

public interface LogRepository extends JpaRepository<LogEntity, Long> {
}
