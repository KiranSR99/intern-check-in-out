

package com.aadim.project.service.impl;

import com.aadim.project.dto.request.ScheduleRequest;
import com.aadim.project.dto.request.ScheduleUpdateRequest;
import com.aadim.project.dto.response.ScheduleResponse;
import com.aadim.project.dto.response.ScheduleStatusResponse;
import com.aadim.project.entity.Intern;
import com.aadim.project.entity.Schedule;
import com.aadim.project.entity.User;
import com.aadim.project.repository.InternRepository;
import com.aadim.project.repository.ScheduleRepository;
import com.aadim.project.service.ScheduleService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
            throw new EntityNotFoundException("Intern with user ID " + request.getUserId() + " not found");
        }

        // Check if the intern has already checked in today
        boolean hasCheckedInToday = scheduleRepository.existsByInternAndCheckInTimeAfter(intern, LocalDate.now().atStartOfDay());
        if (hasCheckedInToday) {
            throw new IllegalStateException("Intern has already checked in today");
        }

        // Check if the intern has checked out today
        boolean hasCheckedOutToday = scheduleRepository.existsByInternIdAndCheckOutTimeAfter(intern.getId(), LocalDate.now().atStartOfDay());
        if (hasCheckedOutToday) {
            throw new IllegalStateException("Intern has already checked out today");
        }

        // Save the check-in record
        Schedule schedule = new Schedule();
        schedule.setCheckInTime(LocalDateTime.now());
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

    @Transactional
    public Page<Object> getInternDetail(String fullName,String localDateTime,Pageable pageable) {
//        LocalDateTime now = LocalDateTime.now();
//    PageRequest request = PageRequest.of(page, size);
        LocalDate localDate = LocalDate.parse(localDateTime, DateTimeFormatter.ISO_DATE);
        LocalDateTime startOfToday = LocalDateTime.of(localDate, LocalTime.MIN);
        LocalDateTime endOfToday = LocalDateTime.of(localDate, LocalTime.MAX);

        Page<Map<String, Object>> internDetails = scheduleRepository.getInternDetail(fullName,startOfToday,endOfToday,pageable);

        Map<String, Map<String, Object>> responseMap = new HashMap<>();

        internDetails.forEach(detail -> {
            String id = String.valueOf(detail.get("id"));
            String checkInTime = String.valueOf(detail.get("check_in_time"));
            String uniqueKey = id + "_" + checkInTime; // Unique key for id and check-in time combination

            responseMap.computeIfAbsent(uniqueKey, k -> createNewEntry(detail))
                    .computeIfAbsent("assignedTasks", k -> new ArrayList<>());
            ((List<Map<String, Object>>) responseMap.get(uniqueKey).get("assignedTasks")).add(createTask(detail));
        });
         List<Object> responseList = new ArrayList<>(responseMap.values());
        return new PageImpl<>(responseList, pageable, responseList.size());
        // return new ArrayList<>(responseMap.values());
    }

    private Map<String, Object> createNewEntry(Map<String, Object> detail) {
        Map<String, Object> data = new HashMap<>();
        data.put("id", detail.get("id"));
        data.put("full_name", detail.get("full_name"));
        data.put("check_in_time", detail.get("check_in_time"));
        data.put("check_out_time", detail.get("check_out_time"));
        return data;
    }

    private Map<String, Object> createTask(Map<String, Object> detail) {
        Map<String, Object> task = new HashMap<>();
        task.put("task", detail.get("task"));
        task.put("field_type", detail.get("field_type"));
        task.put("problem", detail.get("problem"));
        task.put("status", detail.get("status"));
        task.put("time_taken", detail.get("time_taken"));
        return task;
    }

    @Override
    public ScheduleStatusResponse getStatusOfSchedule(Integer userId) {
        Intern intern = internRepository.findInternByUserId(userId);
        if (intern == null) {
            throw new EntityNotFoundException("Intern with user ID " + userId + " does not exist.");
        }

        Integer internId = intern.getId();
        ScheduleStatusResponse scheduleStatusResponse = new ScheduleStatusResponse();
        scheduleStatusResponse.setHasCheckedIn(scheduleRepository.findLatestCheckInByInternIdAndDate(internId, LocalDate.now()).isPresent());
        scheduleStatusResponse.setHasCheckedOut(scheduleRepository.findLatestCheckOutByInternIdAndDate(internId, LocalDate.now()).isPresent());

        return scheduleStatusResponse;
    }


}