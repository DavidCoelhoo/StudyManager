package com.davidcoelho.studymanager.entity;


public class Task {
    private static int nextId = 0;
    private Integer id;
    private String name;
    private String subject;

    public Task() {
    }

    public Task(String name, String subject) {
        this.id = ++nextId;
        this.name = name;
        this.subject = subject;
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
}
