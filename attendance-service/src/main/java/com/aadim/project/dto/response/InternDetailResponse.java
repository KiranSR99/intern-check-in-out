package com.aadim.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InternDetailResponse {
    private String fullName;
    private String fieldType;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private String task;
    private String status;
    private Integer timeTaken;
    private String problem;

    public void setTaskResponse(TaskResponse taskResponse) {

    }
}
