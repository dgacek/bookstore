package com.softwaremind.bookstore.service;

import com.softwaremind.bookstore.model.repo.LoggingEventRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LogService {
    private LoggingEventRepo loggingEventRepo;

    public long getRequestCount() {
        return loggingEventRepo.count();
    }
}
