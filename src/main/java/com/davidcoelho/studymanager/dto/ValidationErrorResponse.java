package com.davidcoelho.studymanager.dto;

import java.time.Instant;
import java.util.Map;

public class ValidationErrorResponse extends ErrorResponse {

    private Map<String, String> errors;

    public ValidationErrorResponse(
            Integer status,
            String error,
            String message,
            String path,
            Map<String, String> errors) {

        super(status, error, message, path);
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
