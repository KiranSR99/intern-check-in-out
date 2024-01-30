package com.aadim.project.dto.response;

import com.aadim.project.entity.Supervisior;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupervisiorResponse {
    private Integer id;
    private String fullName;
    private String phone;

    public SupervisiorResponse(Supervisior supervisior) {
        this.fullName = supervisior.getFullName();
        this.phone = supervisior.getPhone();
    }
}
