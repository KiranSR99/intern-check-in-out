package com.aadim.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Intern extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String fullName;
    private String phone;
    @Enumerated
    private FieldType fieldType;

    @ManyToOne
    @JoinColumn(name = "pri_supervisor")
    private Supervisor primarySupervisor;

    @ManyToOne
    @JoinColumn(name = "sec_supervisor")
    private Supervisor secondarySupervisor;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Boolean isActive = true;
}
