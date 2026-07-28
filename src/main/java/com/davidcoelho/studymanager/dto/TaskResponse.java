package com.davidcoelho.studymanager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class TaskResponse {
    private Integer id;
    private String name;
    private String subject;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate deadline;

    public TaskResponse(Integer id, String name, String subject, LocalDate deadline){
        this.id = id;
        this.name = name;
        this.subject = subject;
        this.deadline = deadline;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSubject() {
        return subject;
    }

    public LocalDate getDeadline() {
        return deadline;
    }
}
