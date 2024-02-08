package com.aadim.project.service;

import com.aadim.project.dto.request.LeaveRequest;
import com.aadim.project.dto.response.LeaveResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LeaveService {
    String createLeave(LeaveRequest leave) throws RuntimeException;

    Page<LeaveResponse> getAllLeaves(Pageable pageable);

    LeaveResponse getLeaveById(Integer id);

    LeaveResponse setLeaveStatus(Integer id);

    LeaveResponse setDeclineStatus(Integer id);

    String deleteLeave(Integer id);

    List<LeaveResponse> getInternLeaves(Integer id);
}
