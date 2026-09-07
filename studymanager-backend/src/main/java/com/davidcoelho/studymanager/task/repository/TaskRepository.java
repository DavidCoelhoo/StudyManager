package com.davidcoelho.studymanager.task.repository;

import com.davidcoelho.studymanager.account.entity.User;
import com.davidcoelho.studymanager.task.entity.Task;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findByUserId(Integer userId);

    Optional<Task> findByIdAndUserId(Integer taskId, Integer userId);

    List<Task> findBySubjectIgnoreCaseAndUser(String subject, User user);

    List<Task> findByNameIgnoreCaseAndUser(String name, User user);

    List<Task> findByNameContainingIgnoreCaseAndUserOrSubjectContainingIgnoreCaseAndUser(
            String name,
            User nameUser,
            String subject,
            User subjectUser
    );

}
