package com.davidcoelho.studymanager.controller;

import com.davidcoelho.studymanager.entity.Task;
import com.davidcoelho.studymanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Task> add(@Valid @RequestBody Task newTask) {
        Task createdTask = taskService.addTask(newTask);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @GetMapping
    public List<Task> getTasks() {
        return taskService.listTasks();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Integer id) {
        return taskService.getTaskById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@PathVariable Integer id, @Valid @RequestBody Task updatedTask) {
        Task taskFound = taskService.updateTask(id, updatedTask);
        return ResponseEntity.ok(taskFound);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

}


