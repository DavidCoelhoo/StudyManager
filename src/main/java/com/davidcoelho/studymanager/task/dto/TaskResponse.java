package com.davidcoelho.studymanager.task.dto;

import com.davidcoelho.studymanager.task.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class TaskResponse {
    private Integer id;
    private String name;
    private String subject;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate deadline;
    private TaskStatus taskStatus;

    public TaskResponse(Integer id, String name, String subject, LocalDate deadline, TaskStatus taskStatus){
        this.id = id;
        this.name = name;
        this.subject = subject;
        this.deadline = deadline;
        this.taskStatus = taskStatus;
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

    public TaskStatus getTaskStatus(){return taskStatus;}
}
