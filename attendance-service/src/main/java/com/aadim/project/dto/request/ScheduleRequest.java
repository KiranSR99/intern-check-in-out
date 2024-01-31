package com.aadim.project.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleRequest {
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private Integer internId;
}
