package com.davidcoelho.studymanager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public class TaskRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String subject;
    @JsonFormat(pattern = "dd/MM/yyyy")
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
