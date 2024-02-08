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
import java.util.UUID;

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
        log.info("Checking if email exists");
        User user = userRepository.findByEmail(forgotPasswordRequest.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        if(!user.getEmail().equals(forgotPasswordRequest.getEmail())){
            log.error("Email not found");
        }

        // Generate a random UUID
        UUID uuid = UUID.randomUUID();

        // Extract the least significant 6 digits from the UUID
        long leastSignificantBits = uuid.getLeastSignificantBits();
        int otp = (int) Math.abs(leastSignificantBits % 1000000);

        log.info("OTP generated successfully");
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
        }
        log.info("OTP sent successfully");
        return "OTP sent successfully";
    }

    @Override
    public String validateOtp(ForgotPasswordRequest forgotPasswordRequest) {
        log.info("Validating OTP");
        Otp otp = otpRepository.findByEmail(forgotPasswordRequest.getEmail());
        if(otp == null){
            log.error("OTP not found. Please enter a new OTP");
            throw new RuntimeException("OTP not found. Please enter a new OTP");
        }
        if(!otp.getOtp().equals(forgotPasswordRequest.getOtp())){
            log.error("Invalid OTP");
            throw new RuntimeException("Invalid OTP");
        }
        log.info("OTP validated successfully");
        return "OTP validated successfully";
    }

    @Override
    public String updatePassword(ForgotPasswordRequest forgotPasswordRequest) {
        log.info("Updating password");
        return userService.changePasswordByEmail(forgotPasswordRequest);
    }


}
