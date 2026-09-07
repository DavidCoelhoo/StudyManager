package com.davidcoelho.studymanager.task.service;

import com.davidcoelho.studymanager.account.entity.User;
import com.davidcoelho.studymanager.account.repository.UserRepository;
import com.davidcoelho.studymanager.task.dto.TaskRequest;
import com.davidcoelho.studymanager.task.dto.TaskResponse;
import com.davidcoelho.studymanager.task.entity.Task;
import com.davidcoelho.studymanager.task.enums.TaskStatus;
import com.davidcoelho.studymanager.task.exception.TaskNotFoundException;
import com.davidcoelho.studymanager.task.mapper.TaskMapper;
import com.davidcoelho.studymanager.task.repository.TaskRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskService(
            TaskRepository taskRepository,
            TaskMapper taskMapper,
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public TaskResponse addTask(TaskRequest request) {
        User user = getAuthenticatedUser();

        Task task = taskMapper.toEntity(request);

        task.setTaskStatus(TaskStatus.PENDING);
        task.setUser(user);

        Task savedTask = taskRepository.save(task);

        return taskMapper.toResponse(savedTask);
    }

    public List<TaskResponse> listTasks() {
        Integer userId = getAuthenticatedUser().getId();

        return taskRepository.findByUserId(userId)
                .stream()
                .map(taskMapper::toResponse)
                .toList();
    }

    public TaskResponse getTaskById(Integer id) {
        return taskMapper.toResponse(findTaskByIdOrThrow(id));
    }

    public List<TaskResponse> findTaskBySubject(String subject) {
        User user = getAuthenticatedUser();

        return taskRepository.findBySubjectIgnoreCaseAndUser(subject, user)
                .stream()
                .map(taskMapper::toResponse)
                .toList();
    }

    public List<TaskResponse> findTaskByName(String name){
        User user = getAuthenticatedUser();

        return taskRepository.findByNameIgnoreCaseAndUser(name, user)
                .stream()
                .map(taskMapper::toResponse)
                .toList();
    }

    public List<TaskResponse> searchTasks(String term) {
        User user = getAuthenticatedUser();

        return taskRepository
                .findByNameContainingIgnoreCaseAndUserOrSubjectContainingIgnoreCaseAndUser(
                        term,
                        user,
                        term,
                        user
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
        return taskRepository.findByIdAndUserId(id, getAuthenticatedUser().getId())
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findUserByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
    }
}
