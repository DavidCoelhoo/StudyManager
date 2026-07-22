package com.davidcoelho.studymanager.exception;

import com.davidcoelho.studymanager.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(Integer id){
        super("Task with id "+ id + " was not found.");
    }
}
