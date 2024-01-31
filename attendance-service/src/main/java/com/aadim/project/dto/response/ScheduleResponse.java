package com.aadim.project.dto.response;

import com.aadim.project.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponse {
    private Integer id;
    private LocalDate checkInTime;
    private LocalDate checkOutTime;
    private Integer internId;

    public ScheduleResponse(Schedule schedule){
        this.id = schedule.getId();
        this.checkInTime = schedule.getCheckInTime();
        this.checkOutTime = schedule.getCheckOutTime();
        this.internId = schedule.getIntern().getId();
    }
}
