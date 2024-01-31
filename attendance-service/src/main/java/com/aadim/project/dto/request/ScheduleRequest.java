package com.aadim.project.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleRequest {
    private LocalDate checkInTime;
    private LocalDate checkOutTime;
    private Integer internId;
}
