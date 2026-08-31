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
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Authenticated user not found")
                );

        Task task = taskMapper.toEntity(request);

        task.setTaskStatus(TaskStatus.PENDING);
        task.setUser(user);

        Task savedTask = taskRepository.save(task);

        return taskMapper.toResponse(savedTask);
    }

    public List<TaskResponse> listTasks() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(()->
                        new RuntimeException("Authenticated user not found")
                );
        Integer userId = user.getId();


        return taskRepository.findByUserId(userId)
                .stream()
                .map(taskMapper::toResponse)
                .toList();
    }

    public TaskResponse getTaskById(Integer id) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Authenticated user not found"));

        Integer userId = user.getId();

        return taskRepository.findByIdAndUserId(id, userId)
                .map(taskMapper::toResponse)
                .orElseThrow(()-> new TaskNotFoundException(id));
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
