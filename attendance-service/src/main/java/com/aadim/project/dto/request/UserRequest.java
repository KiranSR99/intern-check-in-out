package com.aadim.project.dto.request;

import com.aadim.project.entity.FieldType;
import com.aadim.project.entity.Role;
import com.aadim.project.entity.Supervisor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Super;

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
    private Integer primarySupervisor;
    private Integer secondarySupervisor;
}
