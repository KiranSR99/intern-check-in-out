package com.aadim.project.dto.response;

import com.aadim.project.entity.Intern;
import com.aadim.project.entity.Schedule;
import com.aadim.project.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDetailResponse {
    private Integer id;
    private String checkInTime;
    private LocalDateTime checkOutTime;
    private UserResponse userResponse;

    public ScheduleDetailResponse(Schedule schedule){
        this.id = schedule.getId();
        this.checkInTime = schedule.getCheckInTime().toString();
        this.checkOutTime = schedule.getCheckOutTime();
        Intern intern = schedule.getIntern();
        User user = intern.getUser();
        this.userResponse = new UserResponse(intern, user);
    }
}
