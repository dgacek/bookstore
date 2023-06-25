package com.softwaremind.bookstore.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.web.servlet.HandlerMapping;

public abstract class LoggedController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Marker marker = MarkerFactory.getMarker("API");

    protected void log(HttpServletRequest request) {
        logger.info(marker, "{} {}", request.getMethod(), request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE));
    }

}
