package com.davidcoelho.studymanager.service;

import com.davidcoelho.studymanager.dto.TaskRequest;
import com.davidcoelho.studymanager.dto.TaskResponse;
import com.davidcoelho.studymanager.entity.Task;
import com.davidcoelho.studymanager.enums.TaskStatus;
import com.davidcoelho.studymanager.exception.TaskNotFoundException;
import com.davidcoelho.studymanager.mapper.TaskMapper;
import com.davidcoelho.studymanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {

        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public TaskResponse addTask(TaskRequest request) {
        Task task = taskMapper.toEntity(request);
        task.setTaskStatus(TaskStatus.PENDING);
        Task savedTask = taskRepository.save(task);
        return taskMapper.toResponse(savedTask);
    }

    public List<TaskResponse> listTasks() {

        return taskRepository.findAll()
                .stream()
                .map(taskMapper::toResponse)
                .toList();
    }

    public TaskResponse getTaskById(Integer id) {

        Task task = findTaskByIdOrThrow(id);
        return taskMapper.toResponse(task);
    }

    public List<TaskResponse> findTaskBySubject(String subject) {
        return taskRepository.findBySubjectIgnoreCase(subject)
                .stream()
                .map(taskMapper::toResponse)
                .toList();
    }

    public List<TaskResponse> findTaskByName(String name){
        return taskRepository.findByNameIgnoreCase(name)
                .stream()
                .map(taskMapper::toResponse)
                .toList();
    }

    public List<TaskResponse> searchTasks(String term) {
        return taskRepository
                .findByNameContainingIgnoreCaseOrSubjectContainingIgnoreCase(
                        term,
                        term
                )
                .stream()
                .map(taskMapper::toResponse)
                .toList();
    }

    public TaskResponse updateTask(Integer id, TaskRequest request) {
        Task taskFound = findTaskByIdOrThrow(id);
        taskFound.setName(request.getName());
        taskFound.setSubject(request.getSubject());
        taskFound.setDeadline(request.getDeadline());

        Task savedTask = taskRepository.save(taskFound);

        return taskMapper.toResponse(savedTask);
    }
    public TaskResponse updateStatus(Integer id, TaskStatus status){
        Task taskFound = findTaskByIdOrThrow(id);
        taskFound.setTaskStatus(status);

        Task savedTask = taskRepository.save(taskFound);
        return taskMapper.toResponse(savedTask);
    }

    public void deleteTask(Integer id) {
        Task taskFound = findTaskByIdOrThrow(id);
        taskRepository.delete(taskFound);
    }

    private Task findTaskByIdOrThrow(Integer id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }
}
