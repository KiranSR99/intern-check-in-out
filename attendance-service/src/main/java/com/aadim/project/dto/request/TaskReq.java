package com.aadim.project.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskReq {
    private String task;
    private String status;
    private String problem;
    private String timeTaken;
    private Integer userId;
}
