package com.davidcoelho.studymanager.handler;

import com.davidcoelho.studymanager.exception.TaskNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Void> handleTaskNotFound(
            TaskNotFoundException exception
    ){
        return ResponseEntity.notFound().build();
    }

}
