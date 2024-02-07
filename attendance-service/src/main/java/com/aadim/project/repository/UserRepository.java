package com.aadim.project.repository;

import com.aadim.project.entity.Role;
import com.aadim.project.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);


    @Query("SELECT id FROM User WHERE email = :email")
    Integer findUserIdByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.isActive = true")
    Page<User> findActiveUsers(Pageable pageable);

    @Query("select u from User u WHERE u.isActive = true and u.role =:role")
    Page<User> findActiveUsersByRole(Role role, Pageable pageable);

    @Query("select u from User u where u.email =:email")
    User getUserByEmail(String email);

    boolean existsByEmail(String email);
}
