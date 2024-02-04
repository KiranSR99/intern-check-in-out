package com.aadim.project.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskUpdateRequest {
    private Integer taskId;
    private Integer userId;
    private String task;
    private String problem;
    private String timeTaken;
    private String status;
}
