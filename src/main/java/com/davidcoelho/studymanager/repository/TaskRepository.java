package com.davidcoelho.studymanager.repository;

import com.davidcoelho.studymanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
