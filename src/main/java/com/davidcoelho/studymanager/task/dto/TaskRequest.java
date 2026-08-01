package com.davidcoelho.studymanager.task.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class TaskRequest {
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;
    @NotBlank(message = "Subject cannot be blank")
    @Size(max = 50, message = "Subject cannot exceed 100 characters")
    private String subject;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @FutureOrPresent( message = "The date must be in the present or the future")
    private LocalDate deadline;

    public TaskRequest(){

    }

    public TaskRequest(String name,String subject, LocalDate deadline){
        this.name = name;
        this.subject = subject;
        this.deadline = deadline;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
}
