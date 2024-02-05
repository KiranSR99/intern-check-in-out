package com.aadim.project.service.impl;

import com.aadim.project.dto.request.LeaveRequest;
import com.aadim.project.dto.request.UserRequest;
import com.aadim.project.entity.Intern;
import com.aadim.project.entity.Leave;
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
        log.info("Sending mail for leave to primary and secondary supervisor");
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
        String htmlContent = templateEngine.process("email-leave-template.html",
                context);
        helper.setText(htmlContent,true);
        javaMailSender.send(message);
        log.info("Mail sent successfully");
    }


    @Async
    @Override
    public void sendMailWithOtp(String toEmail, Integer verificationCode) throws MessagingException{

        String sub = "Password Verification Code";
        String content = "Hello "+toEmail+" Your Verification code is :" + verificationCode;
        sendMail(toEmail, sub, content);

    }

    @Async
    @Override
    public void sendMail(String to, String sub, String content) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");
        helper.setTo(to);
        helper.setSubject(sub);
        Context context = new Context();
        context.setVariable("sub", sub);
        context.setVariable("content", content);
        String htmlContent = templateEngine.process("email-template.html",
                context);
        helper.setText(htmlContent,true);
        javaMailSender.send(message);
        log.info("Mail sent successfully");
    }

    @Async
    @Override
    public void leaveResponseMail(String to, String sub, Leave leave) throws MessagingException {
        log.info("Sending leave response mail to Intern");
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");
        helper.setTo(to);
        helper.setSubject(sub);
        Context context = new Context();
        context.setVariable("startDate", leave.getStartDate());
        context.setVariable("endDate", leave.getEndDate());
        context.setVariable("internName", leave.getInternId().getFullName());
        context.setVariable("supervisorEmail", leave.getUpdatedBy());
        String htmlContent = templateEngine.process("email-leave-approval-template.html",
                context);
        helper.setText(htmlContent,true);
        javaMailSender.send(message);
        log.info("Mail sent successfully");
    }

    @Async
    @Override
    public void userCreationMail(String to, String sub, UserRequest userRequest) throws MessagingException {
        log.info("Sending user creation mail..");
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,"UTF-8");
        helper.setTo(to);
        helper.setSubject(sub);
        Context context = new Context();
        context.setVariable("name", userRequest.getFullName());
        context.setVariable("email", to);
        context.setVariable("password", userRequest.getPassword());
        context.setVariable("position", userRequest.getRole());
        String htmlContent = templateEngine.process("user-creation-mail-template.html",
                context);
        helper.setText(htmlContent,true);
        javaMailSender.send(message);
        log.info("Mail sent to user successfully");
    }
}
