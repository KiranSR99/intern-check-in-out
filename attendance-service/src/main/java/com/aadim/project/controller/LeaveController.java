package com.aadim.project.controller;

import com.aadim.project.controller.Base.BaseController;
import com.aadim.project.dto.GlobalApiResponse;
import com.aadim.project.dto.request.LeaveRequest;
import com.aadim.project.service.impl.LeaveServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/leave")
public class LeaveController extends BaseController {
    private final LeaveServiceImpl leaveService;

    @PostMapping("/create")
    public ResponseEntity<GlobalApiResponse> createLeave(@RequestBody LeaveRequest leaveRequest) {

        return successResponse(leaveService.createLeave(leaveRequest));
    }

    @GetMapping("/get")
    public ResponseEntity<GlobalApiResponse> getLeave() {

        return successResponse(leaveService.getAllLeaves());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<GlobalApiResponse> getLeaveById(@PathVariable Integer id) {

        return null;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GlobalApiResponse> updateLeave(@PathVariable Integer id) {

        return null;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GlobalApiResponse> deleteLeave(@PathVariable Integer id) {

        return null;
    }
}
