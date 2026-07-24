package com.davidcoelho.studymanager.service;

import com.davidcoelho.studymanager.entity.Task;
import com.davidcoelho.studymanager.exception.TaskNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    private final List<Task> tasks = new ArrayList<>();
    private Integer nextId = 0;

    public Task addTask(Task task) {
        nextId++;
        task.setId(nextId);
        tasks.add(task);
        return task;
    }

    public List<Task> getTasks() {
        return List.copyOf(tasks);
    }

    public Task getTaskById(Integer id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task updateTask(Integer id, Task task) {
        Task taskFound = getTaskById(id);
        taskFound.setName(task.getName());
        taskFound.setSubject(task.getSubject());
        taskFound.setDeadline(task.getDeadline());

        return taskFound;
    }

    public void deleteTask(Integer id){
        Task taskFound = getTaskById(id);
        tasks.remove(taskFound);
    }
}
