package com.aadim.project.repository;

import com.aadim.project.entity.Role;
import com.aadim.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);


    @Query("SELECT id FROM User WHERE email = :email")
    Integer findUserIdByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.isActive = true")
    List<User> findActiveUsers();

    @Query("select u from User u WHERE u.isActive = true and u.role =:role")
    List<User> findActiveUsersByRole(Role role);
}
