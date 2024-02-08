package com.aadim.project.repository;

import com.aadim.project.entity.Leave;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LeaveRepository extends JpaRepository<Leave, Integer> {
    @Query(nativeQuery = true,
            value = "SELECT * FROM leaves WHERE  is_active= true ORDER BY DESC")
    Page<Leave> findAllByIsActive(Pageable pageable);

    @Query(nativeQuery = true,
            value = "SELECT * FROM leaves WHERE  is_active= true AND id = :id")
    Leave findLeaveById(Integer id);

    @Query(nativeQuery = true,
            value = "SELECT * FROM leaves WHERE  is_active= true AND intern_id = :id ORDER BY DESC")
    List<Leave> findLeaveByInternId(Integer id);
}
