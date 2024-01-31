package com.aadim.project.repository;

import com.aadim.project.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LeaveRepository extends JpaRepository<Leave, Integer> {
    @Query(nativeQuery = true,
            value = "SELECT * FROM leaves WHERE  is_active= true")
    List<Leave> findAllByIsActive();
}
