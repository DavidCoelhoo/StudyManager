package com.davidcoelho.studymanager.mapper;

import com.davidcoelho.studymanager.dto.TaskRequest;
import com.davidcoelho.studymanager.dto.TaskResponse;
import com.davidcoelho.studymanager.entity.Task;
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
