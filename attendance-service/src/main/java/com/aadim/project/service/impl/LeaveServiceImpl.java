package com.aadim.project.service.impl;

import com.aadim.project.dto.request.LeaveRequest;
import com.aadim.project.dto.response.LeaveResponse;
import com.aadim.project.entity.Intern;
import com.aadim.project.entity.Leave;
import com.aadim.project.repository.InternRepository;
import com.aadim.project.repository.LeaveRepository;
import com.aadim.project.service.LeaveService;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
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
    public String createLeave(LeaveRequest leaveRequest) {
        log.info("Creating leave record for intern");

        Leave leave = new Leave();
        Intern intern = internRepository.findInternByUserId(leaveRequest.getInternId());
        if(intern == null){
            log.error("Intern not found");
            throw new RuntimeException("Intern not found");
        }
        leave.setInternId(intern);
        leave.setNoOfDays(leaveRequest.getNoOfDays());
        leave.setStartDate(leaveRequest.getStartDate());
        leave.setEndDate(leaveRequest.getEndDate());
        leave.setReason(leaveRequest.getReason());
        leave.setStatus("Pending");
        leave.setActive(true);

        String primarySupervisorEmail = intern.getPrimarySupervisor().getUser().getEmail();
        String secondarySupervisorEmail = intern.getSecondarySupervisor().getUser().getEmail();

        try{
            log.info("Sending mail to primary and secondary supervisor");
            mailServiceImpl.sendHtmlMail(
                    primarySupervisorEmail,
                    "Leave Request",
                    leaveRequest,
                    intern
            );
            mailServiceImpl.sendHtmlMail(
                    secondarySupervisorEmail,
                    "Leave Request",
                    leaveRequest,
                    intern
            );
        }catch (MessagingException e){
            log.error("Error while sending mail");
        }

        leaveRepository.save(leave);

        log.info("Leave record created successfully");

        return "Leave record created successfully";
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
        log.info("Returning leave record by id");
        if(!leaveRepository.existsById(id)){
            log.error("Leave record not found");
            throw new EntityNotFoundException("Leave record not found");
        }
        Leave leave = leaveRepository.findLeaveById(id);
        if(leave == null){
            throw new NullPointerException("Leave record not found");
        }
        return new LeaveResponse(leaveRepository.findLeaveById(id));
    }

    @Override
    public LeaveResponse setLeaveStatus(Integer id) {
        log.info("Setting leave status to Approved");
        Leave leave = leaveRepository.getReferenceById(id);
        leave.setStatus("Approved");
        log.info("Leave status Approved successfully");
        return new LeaveResponse(leaveRepository.save(leave));
    }

    @Override
    public LeaveResponse setDeclineStatus(Integer id){
        log.info("Setting leave status to Decline");
        Leave leave = leaveRepository.getReferenceById(id);
        leave.setStatus("Decline");
        log.info("Leave status Decline successfully");
        return new LeaveResponse(leaveRepository.save(leave));
    }

    @Override
    public String deleteLeave(Integer id) {
        Leave leave = leaveRepository.getReferenceById(id);
        leave.setActive(false);
        leaveRepository.save(leave);
        log.info("Leave deleted successfully");
        return "Leave deleted successfully";
    }

    @Override
    public List<LeaveResponse> getInternLeaves(Integer id) {
        List<LeaveResponse> leaveResponseList = new ArrayList<>();
        List<Leave> leaveList = leaveRepository.findLeaveByInternId(internRepository.findInternByUserId(id).getId());

        for (Leave leave : leaveList) {
            leaveResponseList.add(new LeaveResponse(leave));
        }
        log.info("Returning all leave records of intern: "+ id);
        return leaveResponseList;
    }

}
