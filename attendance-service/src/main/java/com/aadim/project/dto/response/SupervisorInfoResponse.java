package com.aadim.project.dto.response;


import com.aadim.project.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private List<InternInfoResponse> internInfoResponses;

    public SupervisorInfoResponse(Integer userId, String fullName, String email, String phone, Role role, Integer id) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }
}
