package com.davidcoelho.studymanager.task.service;

import com.davidcoelho.studymanager.account.entity.User;
import com.davidcoelho.studymanager.account.repository.UserRepository;
import com.davidcoelho.studymanager.task.dto.TaskRequest;
import com.davidcoelho.studymanager.task.entity.Task;
import com.davidcoelho.studymanager.task.enums.TaskStatus;
import com.davidcoelho.studymanager.task.exception.TaskNotFoundException;
import com.davidcoelho.studymanager.task.mapper.TaskMapper;
import com.davidcoelho.studymanager.task.repository.TaskRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TaskServiceTests {
    private final TaskRepository tasks = mock(TaskRepository.class);
    private final UserRepository users = mock(UserRepository.class);
    private final User user = mock(User.class);
    private final TaskService service = new TaskService(tasks, new TaskMapper(), users);

    @BeforeEach
    void authenticate() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("owner@example.com", null));
        when(user.getId()).thenReturn(1);
        when(users.findUserByEmail("owner@example.com")).thenReturn(Optional.of(user));
    }

    @AfterEach
    void clearAuthentication() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void rejectsReadingAndChangingTasksOutsideTheAuthenticatedUserScope() {
        when(tasks.findByIdAndUserId(42, 1)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> service.getTaskById(42));
        assertThrows(TaskNotFoundException.class,
                () -> service.updateTask(42, new TaskRequest("Changed", "Math", null)));
        assertThrows(TaskNotFoundException.class,
                () -> service.updateStatus(42, TaskStatus.COMPLETED));
        assertThrows(TaskNotFoundException.class, () -> service.deleteTask(42));

        verify(tasks, never()).findById(any());
        verify(tasks, never()).save(any());
        verify(tasks, never()).delete(any(Task.class));
    }

    @Test
    void allowsOwnerToReadUpdateCompleteAndDeleteTask() {
        Task task = new Task("Original", "Math", null);
        task.setUser(user);
        when(tasks.findByIdAndUserId(42, 1)).thenReturn(Optional.of(task));
        when(tasks.save(task)).thenReturn(task);

        assertNotNull(service.getTaskById(42));
        service.updateTask(42, new TaskRequest("Changed", "Physics", null));
        assertEquals("Changed", task.getName());
        assertEquals("Physics", task.getSubject());
        service.updateStatus(42, TaskStatus.COMPLETED);
        assertEquals(TaskStatus.COMPLETED, task.getTaskStatus());
        service.deleteTask(42);
        verify(tasks).delete(task);
    }

    @Test
    void scopesEveryListAndBothSearchAlternativesToAuthenticatedUser() {
        when(tasks.findByUserId(1)).thenReturn(List.of());
        when(tasks.findBySubjectIgnoreCaseAndUser("Math", user)).thenReturn(List.of());
        when(tasks.findByNameIgnoreCaseAndUser("Study", user)).thenReturn(List.of());
        when(tasks.findByNameContainingIgnoreCaseAndUserOrSubjectContainingIgnoreCaseAndUser(
                "term", user, "term", user)).thenReturn(List.of());

        assertTrue(service.listTasks().isEmpty());
        assertTrue(service.findTaskBySubject("Math").isEmpty());
        assertTrue(service.findTaskByName("Study").isEmpty());
        assertTrue(service.searchTasks("term").isEmpty());

        verify(tasks).findByUserId(1);
        verify(tasks).findBySubjectIgnoreCaseAndUser("Math", user);
        verify(tasks).findByNameIgnoreCaseAndUser("Study", user);
        verify(tasks).findByNameContainingIgnoreCaseAndUserOrSubjectContainingIgnoreCaseAndUser(
                "term", user, "term", user);
        verifyNoMoreInteractions(tasks);
    }

    @Test
    void assignsNewTaskToAuthenticatedUser() {
        when(tasks.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));
        service.addTask(new TaskRequest("Study", "Math", null));
        verify(tasks).save(argThat(task -> task.getUser() == user
                && task.getTaskStatus() == TaskStatus.PENDING));
    }
}
