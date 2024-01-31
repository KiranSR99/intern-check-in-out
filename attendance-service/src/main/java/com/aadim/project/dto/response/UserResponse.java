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
    private String role;
    private FieldType fieldType;

    public UserResponse(Admin admin1, Login login1) {
        this.userId = admin1.getId();
        this.fullName = admin1.getFullName();
        this.email = login1.getEmail();
        this.phone = admin1.getPhone();
        this.role = login1.getRole();
    }

    public UserResponse(Supervisor supervisor1, Login login1) {
        this.userId = supervisor1.getId();
        this.fullName = supervisor1.getFullName();
        this.email = login1.getEmail();
        this.phone = supervisor1.getPhone();
        this.role = login1.getRole();
    }


    public UserResponse(Intern intern1, Login login1) {
        this.userId = intern1.getId();
        this.fullName = intern1.getFullName();
        this.email = login1.getEmail();
        this.phone = intern1.getPhone();
        this.role = login1.getRole();
        this.fieldType = intern1.getFieldType();
    }
}
