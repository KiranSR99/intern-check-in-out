package com.aadim.project.repository;

import com.aadim.project.entity.Intern;
import com.aadim.project.entity.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InternRepository extends JpaRepository<Intern, Integer> {

    @Query("SELECT i FROM Intern i WHERE i.user.id =:id")
    Intern findInternByUserId(Integer id);

    @Query("SELECT i FROM Intern i WHERE i.primarySupervisor =:primarySupervisor")
    List<Intern> findInternByPrimarySupervisor(Supervisor primarySupervisor);

    @Query("SELECT i FROM Intern i WHERE i.primarySupervisor =:secondarySupervisor")
    List<Intern> findInternBySecondarySupervisor(Supervisor secondarySupervisor);

    @Query("SELECT i FROM Intern i WHERE i.isActive = true ")
    List<Intern> findActiveSupervisors();

    @Query("SELECT i FROM Intern i WHERE i.isActive = true and i.primarySupervisor=:supervisor or i.secondarySupervisor=:supervisor")
    List<Intern> findActiveInternsBySupervisor(Supervisor supervisor);
}
