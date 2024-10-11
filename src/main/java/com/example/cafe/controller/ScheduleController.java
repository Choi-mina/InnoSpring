package com.example.cafe.controller;

import com.example.cafe.Service.ScheduleService;
import com.example.cafe.dto.ScheduleDto;
import com.example.cafe.entity.ResultEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/save")
    public ResultEntity addSchedule(@RequestBody ScheduleDto scheduleDto) {
        ResultEntity result = new ResultEntity();

        // Date + Time 데이터 타입 변경
        String dateTimeString = scheduleDto.getScheduleDate() + " " + scheduleDto.scheduleTime + ":00";
        Timestamp timestamp = Timestamp.valueOf(dateTimeString);
        scheduleDto.setScheduleDateTime(timestamp);
        try {
            scheduleService.saveSchedule(scheduleDto);

            // schedule save success
            result.setCode("0000");
            result.setMessage("Schedule Save Success");
            log.info("Schedule Save Success");
        } catch (Exception e) {
            log.error("Schedule-save Fail");
            return new ResultEntity(e);
        }
        return result;
    }
}
