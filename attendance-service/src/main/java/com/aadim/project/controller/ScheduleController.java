package com.aadim.project.controller;

import com.aadim.project.controller.Base.BaseController;
import com.aadim.project.dto.GlobalApiResponse;
import com.aadim.project.dto.request.ScheduleRequest;
import com.aadim.project.dto.request.ScheduleUpdateRequest;
import com.aadim.project.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.internal.LoadingCache;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/schedule")
@RequiredArgsConstructor
public class ScheduleController extends BaseController {
    private final ScheduleService scheduleService;

    @PreAuthorize("hasAuthority('INTERN')")
    @PostMapping("/checkIn")
    public ResponseEntity<GlobalApiResponse> saveCheckIn(@RequestBody ScheduleRequest request) {
        return successResponse(scheduleService.saveCheckIn(request), "Checked in successfully");
    }

    @PreAuthorize("hasAuthority('INTERN')")
    @PutMapping("/checkOut")
    public ResponseEntity<GlobalApiResponse> saveCheckOut(@RequestBody ScheduleUpdateRequest request) {
        return successResponse(scheduleService.updateCheckOut(request), "Checked out successfully");
    }

    @GetMapping("/fetchAll")
    public ResponseEntity<GlobalApiResponse> fetchAll() {
        return successResponse(scheduleService.fetchAll(), "Data fetched successfully");
    }


//    @GetMapping("/fetchAll/{userId}")
//    public ResponseEntity<GlobalApiResponse> fetchAllByUserId(@PathVariable Integer userId){
//        return successResponse(scheduleService.fetchAllByUserId(userId), "Data fetched successfully");
//    }


//    @PreAuthorize("hasAuthority('INTERN')")
//    @GetMapping("/details")
//    public List<Map<String, Object>> getInternDetail(
//            @RequestParam(name = "page", defaultValue = "0") int page,
//            @RequestParam(name = "size", defaultValue = "9") int size
//    ) throws Exception {
//         return scheduleService.getInternDetail(page, size);
//
//    }


    @GetMapping("/details")
    public ResponseEntity<?> getInternDetail(
            @RequestParam(value = "fullName", required = false, defaultValue = "") String fullName,
            @RequestParam(value = "dateParam", required = false, defaultValue = "")String localDateTime,
            @PageableDefault Pageable pageable
    ) throws Exception {
        return ResponseEntity.ok(scheduleService.getInternDetail(fullName,localDateTime, pageable));
    }


//    To-do
    @GetMapping("/getStatusOfCheckin/{userId}")
    public ResponseEntity<GlobalApiResponse> getStatusOfCheckin(@PathVariable Integer userId) {
        return successResponse(scheduleService.getStatusOfSchedule(userId));
    }

}

