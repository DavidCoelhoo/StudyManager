package com.davidcoelho.studymanager.dto;

import com.davidcoelho.studymanager.enums.TaskStatus;
import jakarta.validation.constraints.NotNull;

public class TaskStatusRequest {

    @NotNull
    private TaskStatus status;

    public TaskStatusRequest(){

    }

    public TaskStatusRequest( TaskStatus status){
        this.status = status;
    }
    public TaskStatus getStatus(){
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
