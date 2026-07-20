package com.davidcoelho.studymanager.controller;

import com.davidcoelho.studymanager.entity.Task;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {

    Task task = new Task(90, "Algebra", "matematica");
    @GetMapping ("/")
    public Task task(){
        return task;
    }
}
