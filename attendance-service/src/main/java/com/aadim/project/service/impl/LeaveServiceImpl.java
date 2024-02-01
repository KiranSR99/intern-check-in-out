package com.aadim.project.service.impl;

import com.aadim.project.dto.request.LeaveRequest;
import com.aadim.project.dto.response.LeaveResponse;
import com.aadim.project.entity.Intern;
import com.aadim.project.entity.Leave;
import com.aadim.project.repository.InternRepository;
import com.aadim.project.repository.LeaveRepository;
import com.aadim.project.service.LeaveService;
import jakarta.mail.MessagingException;
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

    @Autowired
    private InternRepository internRepository;

    @Autowired
    private MailServiceImpl mailServiceImpl;
    @Override
    public LeaveResponse createLeave(LeaveRequest leaveRequest) {
        log.info("Creating leave record for intern");
        Leave leave = new Leave();
        Intern intern = internRepository.findInternByUserId(leaveRequest.getInternId());
        leave.setInternId(intern);
        leave.setReason(leaveRequest.getReason());
        leave.setStatus("Pending");
        leave.setActive(true);
        Leave savedLeave = leaveRepository.save(leave);

        try{
            mailServiceImpl.sendHtmlMail(
                    "supervisor@yopmail.com",
                    "Leave Request",
                    leaveRequest
            );
            mailServiceImpl.sendHtmlMail(
                    "supervisor2@yopmail.com",
                    "Leave Request",
                    leaveRequest
            );
        }catch (MessagingException e){
            log.error("Error while sending mail");
        }

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
