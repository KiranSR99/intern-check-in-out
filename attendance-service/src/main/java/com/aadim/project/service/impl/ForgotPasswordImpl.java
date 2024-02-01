package com.aadim.project.service.impl;

import com.aadim.project.dto.request.ForgotPasswordRequest;
import com.aadim.project.entity.Otp;
import com.aadim.project.entity.User;
import com.aadim.project.repository.OtpRepository;
import com.aadim.project.repository.UserRepository;
import com.aadim.project.service.ForgotPasswordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class ForgotPasswordImpl implements ForgotPasswordService {

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private MailServiceImpl mailService;
    @Override
    public String sendOtp(ForgotPasswordRequest forgotPasswordRequest) {
        User user = userRepository.findById(forgotPasswordRequest.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        if(!user.getEmail().equals(forgotPasswordRequest.getEmail())){
            throw new RuntimeException("Email not found");
        }

        int otp = (int) (Math.random() * (1000000 - 100000 + 1) + 100000);
        Otp otpEntity = new Otp();
        otpEntity.setEmail(forgotPasswordRequest.getEmail());
        otpEntity.setOtp(otp);
        otpEntity.setCreatedAt(LocalDateTime.now());
        otpEntity.setPurpose("Forgot Password");
        otpRepository.save(otpEntity);

        try {
            mailService.sendMailWithOtp(forgotPasswordRequest.getEmail(), otp);
        } catch (Exception e) {
            log.error("Error while sending mail", e);
            throw new RuntimeException("Error while sending mail");
        }
        log.info("OTP sent successfully");
        return "OTP sent successfully";
    }

    @Override
    public String validateOtp(ForgotPasswordRequest forgotPasswordRequest) {
        Otp otp = otpRepository.findByEmail(forgotPasswordRequest.getEmail());
        if(otp == null){
            throw new RuntimeException("OTP not found. Please enter a new OTP");
        }
        if(otp.getOtp() != forgotPasswordRequest.getOtp()){
            throw new RuntimeException("Invalid OTP");
        }
        return "OTP validated successfully";
    }

    @Override
    public String updatePassword(ForgotPasswordRequest forgotPasswordRequest) {
        return null;
    }


}
