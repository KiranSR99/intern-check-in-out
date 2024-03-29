package com.aadim.project.dto.response;

import com.aadim.project.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Integer userId;
    private String fullName;
    private String email;
    private String phone;
    private Role role;
    private FieldType fieldType;
    private SupervisorInfoResponse primarySupervisor;
    private SupervisorInfoResponse secondarySupervisor;
    private Integer internId;
    private Integer supervisorId;

    public UserResponse(Admin admin1, User user) {
        this.userId = admin1.getId();
        this.fullName = admin1.getFullName();
        this.email = user.getEmail();
        this.phone = admin1.getPhone();
        this.role = user.getRole();
    }

    public UserResponse(Supervisor supervisor1, User login1) {
        this.userId = supervisor1.getId();
        this.fullName = supervisor1.getFullName();
        this.email = login1.getEmail();
        this.phone = supervisor1.getPhone();
        this.role = login1.getRole();
        this.supervisorId = supervisor1.getId();
    }


    public UserResponse(Intern intern1, User login1) {
        this.userId = login1.getId();
        this.internId = intern1.getId();
        this.fullName = intern1.getFullName();
        this.email = login1.getEmail();
        this.phone = intern1.getPhone();
        this.role = login1.getRole();
        this.fieldType = intern1.getFieldType();
        this.primarySupervisor = mapSupervisorInfo(intern1.getPrimarySupervisor());
        this.secondarySupervisor = mapSupervisorInfo(intern1.getSecondarySupervisor());
    }

    //helper method to map Supervisor to SupervisorInfoResponse
    private SupervisorInfoResponse mapSupervisorInfo(Supervisor supervisor) {
        if (supervisor != null) {
            return new SupervisorInfoResponse(
                    supervisor.getUser().getId(),
                    supervisor.getFullName(),
                    supervisor.getUser().getEmail(),
                    supervisor.getPhone(),
                    supervisor.getUser().getRole(),
                    supervisor.getId()
            );
        }
        return null;
    }
}
