package com.softwaremind.bookstore.model.entity.logback;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "logging_event")
public class LoggingEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "timestmp")
    private Long timestamp;

    @Column(name = "formatted_message", nullable = false)
    private String formattedMessage;

    @Column(name = "logger_name", nullable = false, length = 254)
    private String loggerName;

    @Column(name = "level_string", nullable = false, length = 254)
    private String levelString;

    @Column(name = "thread_name", length = 254)
    private String threadName;

    @Column(name = "reference_flag")
    private Short referenceFlag;

    @Column(name = "arg0", length = 254)
    private String arg0;

    @Column(name = "arg1", length = 254)
    private String arg1;

    @Column(name = "arg2", length = 254)
    private String arg2;

    @Column(name = "arg3", length = 254)
    private String arg3;

    @Column(name = "caller_filename", nullable = false, length = 254)
    private String callerFilename;

    @Column(name = "caller_class", nullable = false, length = 254)
    private String callerClass;

    @Column(name = "caller_method", nullable = false, length = 254)
    private String callerMethod;

    @Column(name = "caller_line", nullable = false, length = 4)
    private String callerLine;
}