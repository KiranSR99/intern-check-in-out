package com.aadim.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Supervisior {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

}
