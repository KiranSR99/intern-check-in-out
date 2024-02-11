package com.aadim.project.dto.request;

import com.aadim.project.entity.FieldType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class UserUpdateRequest {
    private Integer userId;
    private String fullName;
    private String phone;
    private FieldType fieldType;
    private Integer primarySupervisor;
    private Integer secondarySupervisor;
}
