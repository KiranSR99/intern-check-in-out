package com.aadim.project.service.impl;

import com.aadim.project.dto.request.ScheduleRequest;
import com.aadim.project.dto.request.ScheduleUpdateRequest;
//import com.aadim.project.dto.response.ScheduleDetailResponse;
import com.aadim.project.dto.response.ScheduleResponse;
import com.aadim.project.entity.Intern;
import com.aadim.project.entity.Schedule;
import com.aadim.project.repository.InternRepository;
import com.aadim.project.repository.ScheduleRepository;
import com.aadim.project.service.ScheduleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
@Configuration
@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private InternRepository internRepository;


    @Override
    public ScheduleResponse saveCheckIn(ScheduleRequest request) {
        Intern intern = internRepository.findInternByUserId(request.getUserId());
        if (intern == null) {
            throw new RuntimeException("User not found");
        }
        Optional<Schedule> existingSchedule = scheduleRepository.findByInternIdAndCheckOutTimeIsNull(intern.getId());
        if (existingSchedule.isPresent()) {
            throw new RuntimeException("User has already checked in");
        }
        Schedule schedule = new Schedule();
        schedule.setCheckInTime(LocalDateTime.now());
        schedule.setCheckOutTime(request.getCheckOutTime());
        schedule.setIntern(intern);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponse(savedSchedule);
    }


    @Override
    public ScheduleResponse updateCheckOut(ScheduleUpdateRequest request) {
        Intern intern = internRepository.findInternByUserId(request.getUserId());
        if (intern == null) {
            throw new RuntimeException("User not found");
        }
        Optional<Schedule> existingSchedule = scheduleRepository.findByInternIdAndCheckOutTimeIsNull(intern.getId());
        if (!existingSchedule.isPresent()) {
            throw new RuntimeException("User has not checked in or has already checked out");
        }
        Schedule schedule = existingSchedule.get();
        schedule.setCheckOutTime(LocalDateTime.now());
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponse(savedSchedule);
    }

    @Override
    public List<ScheduleResponse> fetchAll() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream().map(ScheduleResponse::new).collect(Collectors.toList());
    }

    @Transactional
    public List<Map<String, Object>> getInternDetail() {
        return scheduleRepository.getInternDetail();
    }





}
