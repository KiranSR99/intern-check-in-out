package com.aadim.project.controller;

import com.aadim.project.controller.Base.BaseController;
import com.aadim.project.dto.GlobalApiResponse;
import com.aadim.project.dto.request.ScheduleRequest;
import com.aadim.project.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/schedule")
@RequiredArgsConstructor
public class ScheduleController extends BaseController {
    private final ScheduleService scheduleService;

    @PostMapping("/checkIn")
    public ResponseEntity<GlobalApiResponse> saveCheckIn(@RequestBody ScheduleRequest request){
        return successResponse(scheduleService.saveCheckIn(request), "Checked in successfully");
    }

}
