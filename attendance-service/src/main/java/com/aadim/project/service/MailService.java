package com.aadim.project.service;

import com.aadim.project.dto.request.LeaveRequest;
import com.aadim.project.dto.request.UserRequest;
import com.aadim.project.entity.Intern;
import com.aadim.project.entity.Leave;
import jakarta.mail.MessagingException;

public interface MailService {
    void userCreationMail(String to, String sub, UserRequest userRequest) throws MessagingException;
    void sendHtmlMail(String to, String sub, LeaveRequest leaveRequest, Intern intern) throws MessagingException;

    void sendMail(String to, String sub, String content) throws MessagingException;

    void sendMailWithOtp(String toEmail, Integer otp) throws MessagingException;
    void leaveResponseMail(String toEmail, String sub, Leave leave) throws MessagingException;
}
