package com.softwaremind.bookstore.model.repo;

import com.softwaremind.bookstore.model.entity.logback.LoggingEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoggingEventRepo extends JpaRepository<LoggingEvent, Long> {
}
