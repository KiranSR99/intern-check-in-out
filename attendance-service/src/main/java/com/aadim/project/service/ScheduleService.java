package com.aadim.project.service;

import com.aadim.project.dto.request.ScheduleRequest;
import com.aadim.project.dto.request.ScheduleUpdateRequest;
import com.aadim.project.dto.response.ScheduleResponse;

public interface ScheduleService {

    ScheduleResponse saveCheckIn(ScheduleRequest request);

    ScheduleResponse updateCheckOut(ScheduleUpdateRequest request);

//    @Transactional
//    ScheduleResponse updateUserCheckoutTime(Integer userId);
//
//    void updateCheckOutTime(Intern intern);
}
