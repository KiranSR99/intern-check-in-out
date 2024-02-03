package com.aadim.project.service;

import com.aadim.project.dto.request.ScheduleRequest;
import com.aadim.project.dto.request.ScheduleUpdateRequest;
import com.aadim.project.dto.response.ScheduleResponse;

import java.util.List;
import java.util.Map;

public interface ScheduleService {

    ScheduleResponse saveCheckIn(ScheduleRequest request);

    ScheduleResponse updateCheckOut(ScheduleUpdateRequest request);

    List<ScheduleResponse> fetchAll();


    List<Map<String, Object>> getInternDetail();
}

