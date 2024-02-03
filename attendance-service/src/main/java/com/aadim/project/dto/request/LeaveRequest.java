package com.aadim.project.dto.request;

import com.aadim.project.entity.Intern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequest {
    private Integer internId;
    private Integer noOfDays;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
}
