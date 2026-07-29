package com.davidcoelho.studymanager.entity;


import com.davidcoelho.studymanager.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    private String name;
    @NotBlank
    private String subject;
    @JsonProperty("deadline")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate deadline;
    @Enumerated(EnumType.STRING)
    @Column(name = "task_status", nullable = false)
    private TaskStatus status;

    public Task() {
    }

    public Task(String name, String subject, LocalDate deadline) {
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

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LocalDate getDeadline() {return deadline; }

    @JsonProperty("deadline")
    public void setDeadline(LocalDate deadline) {this.deadline = deadline; }

    public TaskStatus getTaskStatus(){return status;}

    public void setTaskStatus(TaskStatus taskStatus){
        this.status = taskStatus;
    }
}
