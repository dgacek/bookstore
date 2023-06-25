package com.softwaremind.bookstore.controller;

import com.softwaremind.bookstore.service.LogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class LogController extends LoggedController {
    private LogService logService;

    @GetMapping(path = "/api/v1/requests")
    public long getRequestCount(HttpServletRequest request) {
        log(request);
        return logService.getRequestCount();
    }
}
