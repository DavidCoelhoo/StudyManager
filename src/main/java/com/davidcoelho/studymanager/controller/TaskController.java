package com.davidcoelho.studymanager.controller;

import com.davidcoelho.studymanager.dto.TaskRequest;
import com.davidcoelho.studymanager.dto.TaskResponse;
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
    public ResponseEntity<TaskResponse> add(@Valid @RequestBody TaskRequest request) {
        TaskResponse response = taskService.addTask(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public List<TaskResponse> getTasks() {
        return taskService.listTasks();
    }

    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Integer id) {
        return taskService.getTaskById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> update(@PathVariable Integer id, @Valid @RequestBody TaskRequest request) {
        TaskResponse response = taskService.updateTask(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

}


