package com.aadim.project.service;

import com.aadim.project.dto.request.LeaveRequest;
import jakarta.mail.MessagingException;

public interface MailService {
    void sendHtmlMail(String to, String sub, LeaveRequest leaveRequest) throws MessagingException;
}
