package com.aadim.project.dto.request;

import com.aadim.project.entity.Supervisior;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InternRequest {
    private String fullName;
    private String phone;
    private String fieldType;
    private Integer supervisiorId;


}
