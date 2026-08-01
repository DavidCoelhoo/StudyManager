package com.davidcoelho.studymanager.task.repository;

import com.davidcoelho.studymanager.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findBySubjectIgnoreCase(String subject);

    List<Task> findByNameIgnoreCase(String name);

    List<Task> findByNameContainingIgnoreCaseOrSubjectContainingIgnoreCase(
            String name,
            String subject
    );

}
