package com.aadim.project.dto.response;

import com.aadim.project.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponse {
    private String task;
    private String status;
    private String problem;
    private String timeTaken;

    public TaskResponse(Task savedTask){
        this.task = savedTask.getTask();
        this.status = savedTask.getStatus();
        this.problem = savedTask.getProblem();
        this.timeTaken = savedTask.getTimeTaken();
    }
}