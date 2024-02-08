package com.aadim.project.controller;

import com.aadim.project.controller.Base.BaseController;
import com.aadim.project.dto.GlobalApiResponse;
import com.aadim.project.dto.request.LeaveRequest;
import com.aadim.project.service.impl.LeaveServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/leave")
public class LeaveController extends BaseController {
    private final LeaveServiceImpl leaveService;

    @PreAuthorize("hasAuthority('INTERN')")
    @PostMapping("/create")
    public ResponseEntity<GlobalApiResponse> createLeave(@RequestBody LeaveRequest leaveRequest) {

        return successResponse(leaveService.createLeave(leaveRequest));
    }

    @GetMapping("/get")
    public ResponseEntity<GlobalApiResponse> getLeave(@PageableDefault Pageable pageable) {

        return successResponse(leaveService.getAllLeaves(pageable));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<GlobalApiResponse> getLeaveById(@PathVariable Integer id) {

        return successResponse(leaveService.getLeaveById(id));
    }

    @PreAuthorize("hasAuthority('SUPERVISOR')")
    @PatchMapping("/approved-leave/{id}")
    public ResponseEntity<GlobalApiResponse> updateLeave(@PathVariable Integer id) {

        return successResponse(leaveService.setLeaveStatus(id));
    }

    @PreAuthorize("hasAuthority('SUPERVISOR')")
    @PatchMapping("decline-leave/{id}")
    public ResponseEntity<GlobalApiResponse> setDeclineStatus(@PathVariable Integer id){
        return successResponse(leaveService.setDeclineStatus(id));
    }

    @PreAuthorize("hasAuthority('INTERN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GlobalApiResponse> deleteLeave(@PathVariable Integer id) {

        return successResponse(leaveService.deleteLeave(id));
    }

    @PreAuthorize("hasAuthority('INTERN')")
    @GetMapping("/getIntern/{id}")
    public ResponseEntity<GlobalApiResponse> getInternLeaves(@PathVariable Integer id) {

        return successResponse(leaveService.getInternLeaves(id));
    }
}
