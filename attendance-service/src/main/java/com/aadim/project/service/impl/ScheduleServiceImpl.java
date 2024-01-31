package com.aadim.project.service.impl;

import com.aadim.project.dto.request.ScheduleRequest;
import com.aadim.project.dto.response.ScheduleResponse;
import com.aadim.project.entity.Intern;
import com.aadim.project.entity.Schedule;
import com.aadim.project.repository.ScheduleRepository;
import com.aadim.project.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    //save the check in time
    @Override
    public ScheduleResponse saveCheckIn(ScheduleRequest request){
        Schedule schedule = new Schedule();
        schedule.setCheckInTime(request.getCheckInTime());
        schedule.setCheckOutTime(request.getCheckOutTime());
        Intern intern = new Intern();
        intern.setId(request.getInternId());
        schedule.setIntern(intern);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponse(savedSchedule);
    }


}
