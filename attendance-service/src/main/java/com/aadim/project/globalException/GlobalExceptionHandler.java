package com.aadim.project.globalException;

import com.aadim.project.controller.Base.BaseController;
import com.aadim.project.dto.GlobalApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler extends BaseController {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<GlobalApiResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e){
        return errorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), e);
    }
}
