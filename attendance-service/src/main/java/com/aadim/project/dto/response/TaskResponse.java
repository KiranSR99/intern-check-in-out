package com.aadim.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponse {

    private Integer id;

    private String task;

    private String status;

    private String problem;

    private String timeTaken;
}
