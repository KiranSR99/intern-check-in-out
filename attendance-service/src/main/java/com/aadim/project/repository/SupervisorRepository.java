package com.aadim.project.repository;

import com.aadim.project.entity.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SupervisorRepository extends JpaRepository<Supervisor, Integer> {
    @Query("SELECT s FROM Supervisor s WHERE s.isActive = true")
    Supervisor getSupervisorByIsActive();

    @Query("SELECT s FROM Supervisor s WHERE s.user.id =:id")
    Supervisor findSupervisorByUserId(Integer id);

    Supervisor findSupervisorById(Integer primarySupervisor);

    @Query("SELECT s FROM Supervisor s WHERE s.isActive = true ")
    List<Supervisor> findActiveSupervisors();
}
