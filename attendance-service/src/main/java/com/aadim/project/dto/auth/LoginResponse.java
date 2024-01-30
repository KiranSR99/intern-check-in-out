package com.aadim.project.dto.auth;

import org.springframework.context.annotation.Role;

public class LoginResponse {
    private String token;
    private String role;
    private Integer userId;
}
