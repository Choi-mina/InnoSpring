package com.example.cafe.controller;

import com.example.cafe.Service.ScheduleService;
import com.example.cafe.dto.ScheduleDto;
import com.example.cafe.entity.ResultEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

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

    @GetMapping("/find-by-date")
    public ResultEntity findScheduleByDate(@RequestParam("date") String date) {
        ResultEntity result = new ResultEntity();

        try {
            List<ScheduleDto> scheduleDtoList = scheduleService.findByDate(date);

            // schedule find success
            result.setCode("0000");
            result.setMessage("Schedule find Success");
            result.setData(scheduleDtoList);
            log.info("Schedule find Success");
        } catch (Exception e) {
            log.error("Find Schedule Fail");
            return new ResultEntity(e);
        }

        return result;
    }

    @PostMapping("/modify")
    public ResultEntity modifySchedule(@RequestBody ScheduleDto scheduleDto) {
        ResultEntity result = new ResultEntity();

        // Date + Time 데이터 타입 변경
        String dateTimeString = scheduleDto.getScheduleDate() + " " + scheduleDto.scheduleTime + ":00";
        Timestamp timestamp = Timestamp.valueOf(dateTimeString);
        scheduleDto.setScheduleDateTime(timestamp);
        try {
            scheduleService.modifySchedule(scheduleDto);

            // schedule save success
            result.setCode("0000");
            result.setMessage("Schedule Modify Success");
            log.info("Schedule Modify Success");
        } catch (Exception e) {
            log.error("Schedule-modify Fail");
            return new ResultEntity(e);
        }
        return result;
    }

    @PostMapping("/delete")
    public ResultEntity deleteSchedule(@RequestParam("id") int scheduleId) {
        ResultEntity result = new ResultEntity();
        try {
            scheduleService.deleteSchedule(scheduleId);

            // schedule save success
            result.setCode("0000");
            result.setMessage("Schedule Delete Success");
            log.info("Schedule Delete Success");
        } catch (Exception e) {
            log.error("Schedule-delete Fail");
            return new ResultEntity(e);
        }
        return result;
    }
}
