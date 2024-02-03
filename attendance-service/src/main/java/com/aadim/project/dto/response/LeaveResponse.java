package com.aadim.project.dto.response;

import com.aadim.project.entity.Intern;
import com.aadim.project.entity.Leave;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveResponse {
    private Integer id;
    private String internName;
    private Integer noOfDays;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private String status;
    private String createdBy;
    private LocalDateTime createdDate;
    private String updatedBy;
    private LocalDateTime updatedDate;

    public LeaveResponse(Leave leave) {
        this.id = leave.getId();
        this.internName = leave.getInternId().getFullName();
        this.noOfDays = leave.getNoOfDays();
        this.startDate = leave.getStartDate();
        this.endDate = leave.getEndDate();
        this.reason = leave.getReason();
        this.status = leave.getStatus();
        this.createdBy = leave.getCreatedBy();
        this.createdDate = leave.getCreatedDate();
        this.updatedBy = leave.getUpdatedBy();
        this.updatedDate = leave.getUpdatedDate();
    }
}
