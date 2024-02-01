package com.aadim.project.service;

import com.aadim.project.dto.request.LeaveRequest;
import com.aadim.project.entity.Intern;
import jakarta.mail.MessagingException;

public interface MailService {
    void sendHtmlMail(String to, String sub, LeaveRequest leaveRequest, Intern intern) throws MessagingException;

    void sendOtpMail(String to, String sub, String content) throws MessagingException;

    void sendMailWithOtp(String toEmail, Integer otp) throws MessagingException;
}
