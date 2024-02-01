package com.aadim.project.service;

import com.aadim.project.dto.request.ForgotPasswordRequest;

public interface ForgotPasswordService {
    String sendOtp(ForgotPasswordRequest forgotPasswordRequest);

    String validateOtp(ForgotPasswordRequest forgotPasswordRequest);

    String updatePassword(ForgotPasswordRequest forgotPasswordRequest);
}
