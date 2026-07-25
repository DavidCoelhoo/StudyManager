package com.davidcoelho.studymanager.service;

import com.davidcoelho.studymanager.entity.Task;
import com.davidcoelho.studymanager.exception.TaskNotFoundException;
import com.davidcoelho.studymanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> listTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Integer id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task updateTask(Integer id, Task task) {
        Task taskFound = getTaskById(id);
        taskFound.setName(task.getName());
        taskFound.setSubject(task.getSubject());
        taskFound.setDeadline(task.getDeadline());

        return taskRepository.save(taskFound);
    }

    public void deleteTask(Integer id) {
        Task taskFound = getTaskById(id);
        taskRepository.delete(taskFound);
    }
}
