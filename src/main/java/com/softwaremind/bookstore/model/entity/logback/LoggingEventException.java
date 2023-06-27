package com.softwaremind.bookstore.model.entity.logback;

import jakarta.persistence.*;

@Entity
@Table(name = "logging_event_exception")
public class LoggingEventException {
    @EmbeddedId
    private LoggingEventExceptionId id;

    @Column(name = "trace_line", nullable = false, length = 254)
    private String traceLine;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("eventId")
    private LoggingEvent loggingEvent;
}
