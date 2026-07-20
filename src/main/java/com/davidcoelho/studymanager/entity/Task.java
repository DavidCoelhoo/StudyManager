package com.davidcoelho.studymanager.entity;



public class Task {
    private static int nextID = 0;
    private int id;
    private String name;
    private String subject;

    public Task() {
    }
    public Task(int id, String name, String suject){
        this.id = ++Task.nextID;
        this.name = name;
        this.subject = suject;
    }

    public int getId(){
        return id;
    }
    public String  getName(){
        return name;
    }
    public String getSubject(){
        return subject;
    }
}
