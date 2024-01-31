package com.aadim.project.repository;

import com.aadim.project.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

    @Query("SELECT a FROM Admin a WHERE a.user.id =:id")
    Admin findAdminByUserId(Integer id);
}
