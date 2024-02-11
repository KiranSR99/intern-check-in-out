package com.aadim.project.service;

import com.aadim.project.dto.GlobalApiResponse;
import com.aadim.project.dto.request.ScheduleRequest;
import com.aadim.project.dto.request.ScheduleUpdateRequest;
import com.aadim.project.dto.response.ScheduleResponse;
import com.aadim.project.dto.response.ScheduleStatusResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ScheduleService {

    ScheduleResponse saveCheckIn(ScheduleRequest request);

    ScheduleResponse updateCheckOut(ScheduleUpdateRequest request);

    List<ScheduleResponse> fetchAll();



//    List<Map<String, Object>> getInternDetail(int page, int size);

    Page<Object> getInternDetail(String fullName, String localDateTime, Pageable pageable);

    ScheduleStatusResponse getStatusOfSchedule(Integer userId);


//    GlobalApiResponse getStatus();
}

