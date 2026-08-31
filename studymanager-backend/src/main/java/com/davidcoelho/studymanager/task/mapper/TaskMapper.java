package com.davidcoelho.studymanager.task.mapper;

import com.davidcoelho.studymanager.task.dto.TaskRequest;
import com.davidcoelho.studymanager.task.dto.TaskResponse;
import com.davidcoelho.studymanager.task.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task toEntity(TaskRequest request){
        return new Task(
                request.getName(),
                request.getSubject(),
                request.getDeadline()
        );
    }

    public TaskResponse toResponse(Task task){
        return new TaskResponse(
                task.getId(),
                task.getName(),
                task.getSubject(),
                task.getDeadline(),
                task.getTaskStatus()
        );
    }
}
