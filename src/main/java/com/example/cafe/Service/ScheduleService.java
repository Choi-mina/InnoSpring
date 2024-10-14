package com.example.cafe.Service;

import com.example.cafe.Repository.ScheduleRepository;
import com.example.cafe.dto.MemberDto;
import com.example.cafe.dto.ScheduleDto;
import com.example.cafe.entity.Schedule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<ScheduleDto> findByDate(String date) {
        List<Schedule> schedule = scheduleRepository.findByDate(date);
        List<ScheduleDto> scheduleDtoList = new ArrayList<>();

        if(schedule != null)
            for(Schedule s : schedule) {
                ScheduleDto dto = ScheduleDto.builder()
                        .scheduleId(s.getScheduleId())
                        .scheduleName(s.getScheduleName())  // Schedule 객체의 값을 사용
                        .scheduleDate(String.valueOf(s.getScheduleDate()))  // 날짜를 문자열로 변환
                        .build();

                scheduleDtoList.add(dto);  // 리스트에 추가
            }

        return scheduleDtoList;
    }

    public void modifySchedule(ScheduleDto scheduleDto) {
        Schedule schedule = new Schedule();

        // Dto -> Entity
        schedule.setScheduleId(scheduleDto.getScheduleId());
        schedule.setScheduleName(scheduleDto.getScheduleName());
        schedule.setScheduleDate(scheduleDto.getScheduleDateTime());
        schedule.setScheduleDescription(scheduleDto.getScheduleDescription());

        scheduleRepository.save(schedule);
    }

    public void deleteSchedule(int scheduleId) {
        scheduleRepository.deleteById((long) scheduleId);
    }
}
