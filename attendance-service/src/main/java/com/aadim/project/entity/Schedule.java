package com.aadim.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Schedule extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;

//    private boolean isActive;
//    @PrePersist
//    public void prePersist() {
//        this.checkInTime = LocalDateTime.now();
//    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "intern_id")
    private Intern intern;

}
