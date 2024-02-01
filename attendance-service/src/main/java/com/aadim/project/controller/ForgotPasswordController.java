package com.aadim.project.controller;

import com.aadim.project.controller.Base.BaseController;
import com.aadim.project.dto.GlobalApiResponse;
import com.aadim.project.dto.request.ForgotPasswordRequest;
import com.aadim.project.service.impl.ForgotPasswordImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/forgot-password")
@RequiredArgsConstructor
public class ForgotPasswordController extends BaseController {

    @Autowired
    private ForgotPasswordImpl forgotPasswordService;
    @PostMapping("/send-otp")
    public ResponseEntity<GlobalApiResponse> validateEmail(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        return successResponse(forgotPasswordService.sendOtp(forgotPasswordRequest));
    }

    @PostMapping("/validate-otp")
    public ResponseEntity<GlobalApiResponse> validateOtp(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        return successResponse(forgotPasswordService.validateOtp(forgotPasswordRequest));
    }

    @PutMapping("/update-password")
    public ResponseEntity<GlobalApiResponse> updatePassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        return successResponse(forgotPasswordService.updatePassword(forgotPasswordRequest));
    }
}
