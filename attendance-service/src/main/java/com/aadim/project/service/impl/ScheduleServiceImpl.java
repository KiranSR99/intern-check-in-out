package com.aadim.project.service.impl;

import com.aadim.project.dto.request.ScheduleRequest;
import com.aadim.project.dto.request.ScheduleUpdateRequest;
//import com.aadim.project.dto.response.ScheduleDetailResponse;
import com.aadim.project.dto.response.ScheduleResponse;
import com.aadim.project.entity.Intern;
import com.aadim.project.entity.Schedule;
import com.aadim.project.repository.ScheduleRepository;
import com.aadim.project.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    //save the check in time
    @Override
    public ScheduleResponse saveCheckIn(ScheduleRequest request){
//        Schedule existingSchedule = scheduleRepository.findByInternIdAndCheckoutTimeIsNull(request.getInternId()).orElse(null);
//        if (existingSchedule != null) {
//            throw new RuntimeException("User has already checked in");  // Replace with your own exception
//        }
        Schedule schedule = new Schedule();
        schedule.setCheckInTime(request.getCheckInTime());
        schedule.setCheckOutTime(request.getCheckOutTime());
        Intern intern = new Intern();
        intern.setId(request.getInternId());
        schedule.setIntern(intern);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponse(savedSchedule);
    }


    @Override
    public ScheduleResponse updateCheckOut(ScheduleUpdateRequest request){
        Schedule schedule= scheduleRepository.getReferenceById(request.getId());
        schedule.setCheckOutTime(LocalDateTime.now());
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponse(savedSchedule);
    }

    @Override
    public List<ScheduleResponse> fetchAll(){
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream().map(ScheduleResponse::new).collect(Collectors.toList());
    }


}
