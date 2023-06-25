package com.softwaremind.bookstore.model.entity.logback;

import jakarta.persistence.*;

@Entity
@Table(name = "logging_event_property")
public class LoggingEventProperty {
    @EmbeddedId
    private LoggingEventPropertyId id;

    @Column(name = "mapped_value")
    private String mappedValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("eventId")
    private LoggingEvent loggingEvent;
}
