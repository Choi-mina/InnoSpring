package com.example.cafe.Repository.Schedule;

import com.example.cafe.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long>, ScheduleRepositoryCustom {
}
