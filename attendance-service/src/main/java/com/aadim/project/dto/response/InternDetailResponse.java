package com.aadim.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

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
    private LocalDateTime timeTaken;
    private String problem;

    public InternDetailResponse(Map<String, Object> internDetail) {
        this.fullName = (String) internDetail.get("fullName");
        this.fieldType = (String) internDetail.get("fieldType");
        this.checkInTime = (LocalDateTime) internDetail.get("checkInTime");
        this.checkOutTime = (LocalDateTime) internDetail.get("checkOutTime");
        this.task = (String) internDetail.get("task");
        this.status = (String) internDetail.get("status");
        this.timeTaken = (LocalDateTime) internDetail.get("timeTaken");
        this.problem = (String) internDetail.get("problem");
    }
}
