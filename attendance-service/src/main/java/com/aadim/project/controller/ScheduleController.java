package com.aadim.project.controller;

import com.aadim.project.controller.Base.BaseController;
import com.aadim.project.dto.GlobalApiResponse;
import com.aadim.project.dto.request.ScheduleRequest;
import com.aadim.project.dto.request.ScheduleUpdateRequest;
import com.aadim.project.dto.response.ScheduleResponse;
import com.aadim.project.service.ScheduleService;
import com.aadim.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/schedule")
@RequiredArgsConstructor
public class ScheduleController extends BaseController {
    private final ScheduleService scheduleService;

    @PostMapping("/checkIn")
    public ResponseEntity<GlobalApiResponse> saveCheckIn(@RequestBody ScheduleRequest request){
        return successResponse(scheduleService.saveCheckIn(request), "Checked in successfully");
    }

    @PutMapping("/checkOut")
    public ResponseEntity<GlobalApiResponse> saveCheckOut(@RequestBody ScheduleUpdateRequest request){
        return successResponse(scheduleService.updateCheckOut(request), "Checked out successfully");
    }

    @GetMapping("/fetchAll")
    public ResponseEntity<GlobalApiResponse> fetchAll(){
        return successResponse(scheduleService.fetchAll(), "Data fetched successfully");
    }


    @GetMapping("/fetchAll/{userId}")
    public ResponseEntity<GlobalApiResponse> fetchAllByUserId(@PathVariable Integer userId){
        return successResponse(scheduleService.fetchAllByUserId(userId), "Data fetched successfully");
    }

}
