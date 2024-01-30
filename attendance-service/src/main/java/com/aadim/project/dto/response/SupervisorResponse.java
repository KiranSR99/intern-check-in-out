package com.aadim.project.dto.response;

import com.aadim.project.entity.Supervisor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupervisorResponse {
    private Integer id;
    private String fullName;
    private String phone;

    public SupervisorResponse(Supervisor supervisor) {
        this.fullName = supervisor.getFullName();
        this.phone = supervisor.getPhone();
    }
}
