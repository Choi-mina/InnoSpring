package com.example.cafe.Service;

import com.example.cafe.Repository.ScheduleRepository;
import com.example.cafe.dto.ScheduleDto;
import com.example.cafe.entity.Schedule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public void saveSchedule(ScheduleDto scheduleDto) {
        Schedule schedule = new Schedule();
        // DTO -> Entity
        schedule.setScheduleDate(scheduleDto.getScheduleDateTime());
        schedule.setScheduleName(scheduleDto.getScheduleName());
        schedule.setScheduleDescription(scheduleDto.getScheduleDescription());
        scheduleRepository.save(schedule);
    }
}
