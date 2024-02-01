package com.aadim.project.service;

import com.aadim.project.dto.request.LeaveRequest;
import com.aadim.project.dto.response.LeaveResponse;

import java.util.List;

public interface LeaveService {
    String createLeave(LeaveRequest leave);

    List<LeaveResponse> getAllLeaves();

    LeaveResponse getLeaveById(Integer id);

    LeaveResponse setLeaveStatus(Integer id);

    String deleteLeave(Integer id);
}
