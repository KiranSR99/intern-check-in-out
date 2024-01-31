package com.aadim.project.repository;

import com.aadim.project.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
//    @Query(
//
//            nativeQuery = true,
//            value = """
//            SELECT s FROM schedules s WHERE s.intern_id = :internId AND s.check_out_time IS NULL
//            """)
//
//    Optional<Schedule> findByInternIdAndCheckoutTimeIsNull(@Param("internId") Integer internId);

//    Optional<Schedule> findByInternIdAndCheckoutTimeIsNull(Integer userId);


}
