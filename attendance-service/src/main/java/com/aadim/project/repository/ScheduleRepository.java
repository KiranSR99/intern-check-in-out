package com.aadim.project.repository;

import com.aadim.project.dto.response.InternDetailResponse;
import com.aadim.project.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    @Query(

            nativeQuery = true,
            value = """
            SELECT * FROM schedule s WHERE s.intern_id = :internId AND s.check_out_time IS NULL
            """)
    Optional<Schedule> findByInternIdAndCheckOutTimeIsNull(Integer internId);

    @Query(
            nativeQuery = true,
            value = """
            select i.full_name , i.field_type , s.check_in_time, s.check_out_time,
           t.task , t.status ,t.time_taken , t.problem
           from intern i
           full join users u on u.id  = i.user_id
           full join tasks t on u.id  = t.user_id
           full join schedule s on s.intern_id = i.id
            """)
    List<Map<String, Object>> getInternDetail();



}

