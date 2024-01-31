package com.aadim.project.service;

import jakarta.mail.MessagingException;

public interface MailService {
    void sendHtmlMail(String to, String sub, String content) throws MessagingException;
}
