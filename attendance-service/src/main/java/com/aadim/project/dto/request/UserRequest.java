package com.aadim.project.dto.request;

import com.aadim.project.entity.FieldType;
import com.aadim.project.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String fullName;
    private String email;
    private String password;
    private String phone;
    private Role role;
    private FieldType fieldType;
}
