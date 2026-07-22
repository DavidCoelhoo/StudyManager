package com.davidcoelho.studymanager.dto;


import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class ErrorResponse {
    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    public ErrorResponse(Integer status, String error, String message, String path) {
        this.timestamp = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
