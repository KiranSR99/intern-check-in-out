package com.aadim.project.controller.Base;

import com.aadim.project.dto.GlobalApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class BaseController {
    public ResponseEntity<GlobalApiResponse> successResponse(Object data){
        GlobalApiResponse response = new GlobalApiResponse(LocalDateTime.now(), "success"
                ,data, HttpStatus.OK.name());
        return ResponseEntity.ok(response);
    }
    public ResponseEntity<GlobalApiResponse> successResponse(Object data, String message){
        GlobalApiResponse response = new GlobalApiResponse(
                LocalDateTime.now(),
                message,
                data,
                HttpStatus.OK.name()
        );
        return ResponseEntity.ok(response);
    }
    public ResponseEntity<GlobalApiResponse> errorResponse(HttpStatus status, String message, Exception exception) {
        GlobalApiResponse response = new GlobalApiResponse(LocalDateTime.now(), message, exception, HttpStatus.BAD_REQUEST.name());
        response.setMessage(message);
        if (exception != null) {
            response.setData(exception.getMessage());
        }
        return ResponseEntity.status(status).body(response);
    }
}
