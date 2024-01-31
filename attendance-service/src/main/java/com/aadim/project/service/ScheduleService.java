package com.aadim.project.service;

import com.aadim.project.dto.request.ScheduleRequest;
import com.aadim.project.dto.response.ScheduleResponse;

public interface ScheduleService {

    ScheduleResponse saveCheckIn(ScheduleRequest request);

}
