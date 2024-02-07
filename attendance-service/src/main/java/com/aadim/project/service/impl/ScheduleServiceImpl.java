

package com.aadim.project.service.impl;

import com.aadim.project.dto.request.ScheduleRequest;
import com.aadim.project.dto.request.ScheduleUpdateRequest;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
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

        Optional<LocalDateTime> lastScheduleOpt = scheduleRepository.findTopByInternOrderByCheckOutTimeDesc(request.getUserId());
        if (lastScheduleOpt.isPresent()) {
            LocalDateTime lastCheckOutTime = lastScheduleOpt.get();
            if (lastCheckOutTime.toLocalDate().equals(LocalDate.now())) {
                throw new RuntimeException("You can't check in again on the same day you have checked out.");
            }
        }

//        Optional<Schedule> existingSchedule = scheduleRepository.findByInternIdAndCheckOutTimeIsNull(intern.getId());
//        if (existingSchedule.isPresent()) {
//            throw new RuntimeException("User has already checked in");
//        }
        if (scheduleRepository.existsById(intern.getId())) {
            Schedule schedule = scheduleRepository.getLatestScheduleByInternId(intern.getId());
            if (schedule.getCheckOutTime() == null) {
                throw new RuntimeException("User has already checked in");
            }
            Schedule schedule1 = new Schedule();
            schedule1.setCheckInTime(LocalDateTime.now());
            schedule1.setCheckOutTime(null);
            schedule1.setIntern(intern);
            Schedule savedSchedule = scheduleRepository.save(schedule1);
            return new ScheduleResponse(savedSchedule);
        } else {
            Schedule schedule1 = new Schedule();
            schedule1.setCheckInTime(LocalDateTime.now());
            schedule1.setCheckOutTime(null);
            schedule1.setIntern(intern);
            Schedule savedSchedule = scheduleRepository.save(schedule1);
            return new ScheduleResponse(savedSchedule);
        }
    }



    @Override
    public ScheduleResponse updateCheckOut(ScheduleUpdateRequest request) {
        Intern intern = internRepository.findInternByUserId(request.getUserId());
        if (intern == null) {
            throw new RuntimeException("User not found");
        }
//        Optional<Schedule> existingSchedule = scheduleRepository.findByInternIdAndCheckOutTimeIsNull(intern.getId());
//        if (!existingSchedule.isPresent()) {
//            throw new RuntimeException("User has not checked in or has already checked out");
//        }

        Schedule schedule = scheduleRepository.getLatestScheduleByInternId(intern.getId());

//        Schedule schedule = existingSchedule.get();
        schedule.setCheckOutTime(LocalDateTime.now());
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponse(savedSchedule);
    }

    @Override
    public List<ScheduleResponse> fetchAll() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream().map(ScheduleResponse::new).collect(Collectors.toList());
    }


//    @Transactional
//    public List<Map<String, Object>> getInternDetail(int page, int size) {
//        PageRequest request = PageRequest.of(page, size);
//         return scheduleRepository.getInternDetail(request);
//
//    }





}