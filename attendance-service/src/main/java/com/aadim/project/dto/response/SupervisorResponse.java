package com.aadim.project.dto.response;


import com.aadim.project.entity.FieldType;
import com.aadim.project.entity.Role;
import com.aadim.project.entity.Supervisor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupervisorResponse {
    private Integer userId;
    private String fullName;
    private String email;
    private String phone;
    private Role role;
    private Integer supervisorId;
}
