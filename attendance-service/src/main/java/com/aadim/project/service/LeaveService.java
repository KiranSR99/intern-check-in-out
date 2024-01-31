package com.aadim.project.service;

import com.aadim.project.dto.request.LeaveRequest;
import com.aadim.project.dto.response.LeaveResponse;

import java.util.List;

public interface LeaveService {
    LeaveResponse createLeave(LeaveRequest leave);

    List<LeaveResponse> getAllLeaves();
}
