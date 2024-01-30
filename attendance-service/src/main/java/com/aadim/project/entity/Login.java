package com.aadim.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String email;
    private String password;
    private String role;

    @OneToOne
    @JoinColumn(name = "admin_id")
    private Admin admin_id;

    @OneToOne
    @JoinColumn(name = "intern_id")
    private Intern intern_id;

    @OneToOne
    @JoinColumn(name = "supervisior_id")
    private Supervisior supervisior_id;
}
