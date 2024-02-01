package com.aadim.project.dto.request;

import com.aadim.project.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordRequest {
    private Integer userId;
    private String oldPassword;
    private String newPassword;
}
