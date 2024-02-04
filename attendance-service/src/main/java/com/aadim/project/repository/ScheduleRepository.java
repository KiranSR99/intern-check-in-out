package com.aadim.project.repository;

import com.aadim.project.dto.response.InternDetailResponse;
import com.aadim.project.entity.Intern;
import com.aadim.project.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
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


                    SELECT
                        i.full_name,
                        i.field_type,
                        s.check_in_time,
                        s.check_out_time,
                        t.task,
                        t.status,
                        t.time_taken,
                        t.problem
                    FROM
                        intern i
                    JOIN
                        users u ON u.id = i.user_id
                    JOIN
                        tasks t ON u.id = t.user_id
                    JOIN
                        schedule s ON s.intern_id = i.id;
                     """)
    List<Map<String, Object>> getInternDetail();

    @Query(
            nativeQuery = true,
            value = """
                    select s.check_out_time from schedule s
                        join intern i on i.id = s.intern_id
                        where user_id = :userId and s.check_out_time is not null
                        order by s.check_out_time desc limit 1;
            """)
    Optional<LocalDateTime> findTopByInternOrderByCheckOutTimeDesc(@Param("userId") Integer userId);
}


