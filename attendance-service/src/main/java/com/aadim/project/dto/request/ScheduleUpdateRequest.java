package com.aadim.project.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleUpdateRequest {
    private Integer id;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
}
