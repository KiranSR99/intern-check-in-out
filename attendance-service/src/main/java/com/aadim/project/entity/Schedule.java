package com.aadim.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "schedules")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private LocalDate checkInTime;
    private LocalDate checkOutTime;
    @PrePersist
    public void prePersist() {
        this.checkInTime = LocalDate.from(LocalDate.now());
    }
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "intern_id")
    private List<Intern> intern;

}
