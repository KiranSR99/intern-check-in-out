package com.aadim.project.dto.response;


import com.aadim.project.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupervisorInfoResponse {
    private Integer userId;
    private String fullName;
    private String email;
    private String phone;
    private Role role;
    private Integer supervisorId;
}
