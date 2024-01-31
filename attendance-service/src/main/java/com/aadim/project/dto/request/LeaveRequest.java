package com.aadim.project.dto.request;

import com.aadim.project.entity.Intern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequest {
    private Intern internId;
    private String reason;
}
