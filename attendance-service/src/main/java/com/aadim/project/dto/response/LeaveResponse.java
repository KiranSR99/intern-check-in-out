package com.aadim.project.dto.response;

import com.aadim.project.entity.Intern;
import com.aadim.project.entity.Leave;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveResponse {
    private Integer id;
    private Intern internId;
    private String reason;
    private String status;

    public LeaveResponse(Leave leave) {
        this.id = leave.getId();
        this.internId = leave.getInternId();
        this.reason = leave.getReason();
    }
}
