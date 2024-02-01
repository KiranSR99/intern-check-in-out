package com.aadim.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "leaves")
public class Leave {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "intern_id")
    private Intern internId;

    private Integer noOfDays;
    private String startDate;
    private String endDate;

    private String reason;

    private String status;

    private boolean isActive;
}
