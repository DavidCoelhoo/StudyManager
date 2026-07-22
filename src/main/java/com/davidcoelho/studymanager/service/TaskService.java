package com.davidcoelho.studymanager.service;

import com.davidcoelho.studymanager.entity.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    private final List<Task> tasks = new ArrayList<>();

    public Task addTask(Task task) {
        tasks.add(task);
        return task;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Task getTaskById(Integer id) {
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                return task;
            }
        }
        return null;
    }

    public Task updateTask(Integer id, Task task) {
        Task taskFound = getTaskById(id);
        taskFound.setName(task.getName());
        taskFound.setSubject(task.getSubject());

        return taskFound;
    }

    public void deleteTask(Integer id){
        Task taskFound = getTaskById(id);
        tasks.remove(taskFound);
    }
}
