package com.example.cafe.Repository.Schedule;

import com.example.cafe.entity.Schedule;

import java.util.List;

public interface ScheduleRepositoryCustom {
    public List<Schedule> findByDate(String date);
}
