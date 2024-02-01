package com.aadim.project.service.impl;

import com.aadim.project.dto.request.LeaveRequest;
import com.aadim.project.entity.Intern;
import com.aadim.project.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;
    @Async
    @Override
    public void sendHtmlMail(String to, String sub, LeaveRequest leaveRequest, Intern intern) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");
        helper.setTo(to);
        helper.setSubject(sub);
        Context context = new Context();
        context.setVariable("noOfDays", leaveRequest.getNoOfDays());
        context.setVariable("startDate", leaveRequest.getStartDate());
        context.setVariable("endDate", leaveRequest.getEndDate());
        context.setVariable("reason", leaveRequest.getReason());
        context.setVariable("name", intern.getFullName());
        context.setVariable("position", intern.getFieldType());
        context.setVariable("phone", intern.getPhone());
        String htmlContent = templateEngine.process("email-template.html",
                context);
        helper.setText(htmlContent,true);
        javaMailSender.send(message);
    }
}
