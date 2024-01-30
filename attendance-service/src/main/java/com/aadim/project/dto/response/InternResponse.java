package com.aadim.project.dto.response;

import com.aadim.project.entity.Intern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InternResponse {
    private Integer id;
    private String fullName;
    private String phone;
    private String fieldType;
    private Integer supervisiorId;

    public InternResponse(Intern intern) {
        this.id = intern.getId();
        this.fullName = intern.getFullName();
        this.phone = intern.getPhone();
        this.fieldType = intern.getFieldType().toString();
        this.supervisiorId = intern.getSupervisior().getId();
    }
}
