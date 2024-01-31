package com.aadim.project.repository;

import com.aadim.project.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    Schedule findByInternId(Integer internId);

//    Optional<Schedule> findByInternIdAndCheckoutTimeIsNull(Integer userId);
}
