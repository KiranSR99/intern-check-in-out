package com.aadim.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalErrorResponse<T> {
    private LocalDateTime timestamp;
    private String message;
    private T data;
    private String status;
}
