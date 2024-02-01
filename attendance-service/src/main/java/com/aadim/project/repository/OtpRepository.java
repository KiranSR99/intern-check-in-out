package com.aadim.project.repository;

import com.aadim.project.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OtpRepository extends JpaRepository<Otp, Integer> {

    @Query("SELECT o FROM Otp o WHERE o.email = :email ORDER BY o.createdAt DESC LIMIT 1")
    Otp findByEmail(String email);

}
