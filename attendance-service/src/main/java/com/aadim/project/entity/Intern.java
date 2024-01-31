package com.aadim.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Intern {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String fullName;
    private String phone;
    @Enumerated
    private FieldType fieldType;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Boolean isActive = true;
}
