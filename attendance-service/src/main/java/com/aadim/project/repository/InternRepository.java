package com.aadim.project.repository;

import com.aadim.project.entity.Intern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InternRepository extends JpaRepository<Intern, Integer> {

    @Query("SELECT i FROM Intern i WHERE i.user.id =:id")
    Intern findInternByUserId(Integer id);
}
