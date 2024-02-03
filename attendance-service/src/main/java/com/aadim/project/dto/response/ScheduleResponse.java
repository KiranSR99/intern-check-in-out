package com.aadim.project.dto.response;

import com.aadim.project.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponse {
    private Integer id;
    private String checkInTime;
    private LocalDateTime checkOutTime;
    private Integer internId;

    public ScheduleResponse(Schedule schedule){
        this.id = schedule.getId();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this.checkInTime = schedule.getCheckInTime().format(formatter);
        this.checkOutTime = schedule.getCheckOutTime();
        this.internId = schedule.getIntern().getId();
    }

}
