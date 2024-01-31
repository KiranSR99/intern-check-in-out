package com.aadim.project.service.impl;

import com.aadim.project.dto.request.LeaveRequest;
import com.aadim.project.dto.response.LeaveResponse;
import com.aadim.project.entity.Leave;
import com.aadim.project.repository.LeaveRepository;
import com.aadim.project.service.LeaveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LeaveServiceImpl implements LeaveService {
    @Autowired
    private LeaveRepository leaveRepository;
    @Override
    public LeaveResponse createLeave(LeaveRequest leaveRequest) {
        log.info("Creating leave record for intern");
        Leave leave = new Leave();
        leave.setInternId(leave.getInternId());
        leave.setReason(leave.getReason());
        leave.setStatus("Pending");
        leave.setActive(true);
        Leave savedLeave = leaveRepository.save(leave);
        log.info("Leave record created successfully");
        return new LeaveResponse(savedLeave);
    }

    @Override
    public List<LeaveResponse> getAllLeaves() {
        List<LeaveResponse> leaveResponseList = new ArrayList<>();
        List<Leave> leaveList = leaveRepository.findAllByIsActive();

        for (Leave leave : leaveList) {
            leaveResponseList.add(new LeaveResponse(leave));
        }
        log.info("Returning all leave records");
        return leaveResponseList;
    }

    @Override
    public LeaveResponse getLeaveById(Integer id) {
        return new LeaveResponse(leaveRepository.getReferenceById(id));
    }

    @Override
    public LeaveResponse setLeaveStatus(Integer id) {
        Leave leave = leaveRepository.getReferenceById(id);
        leave.setStatus("Approved");
        log.info("Leave status Approved successfully");
        return new LeaveResponse(leaveRepository.save(leave));
    }

    @Override
    public String deleteLeave(Integer id) {
        Leave leave = leaveRepository.getReferenceById(id);
        leave.setActive(false);
        return "Leave deleted successfully";
    }
}
