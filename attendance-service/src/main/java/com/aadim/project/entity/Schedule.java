package com.aadim.project.entity;

import com.fasterxml.jackson.annotation.JacksonInject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
//    @PrePersist
//    public void prePersist() {
//        this.checkInTime = LocalDateTime.now();
//    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "intern_id")
    private Intern intern;

}
