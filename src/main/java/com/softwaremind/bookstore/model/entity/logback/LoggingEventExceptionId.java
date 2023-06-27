package com.softwaremind.bookstore.model.entity.logback;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
class LoggingEventExceptionId implements Serializable {
    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "i")
    private Short i;
}
