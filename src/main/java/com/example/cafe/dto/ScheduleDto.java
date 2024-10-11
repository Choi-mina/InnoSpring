package com.example.cafe.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDto {
    public String scheduleDate;
    public String scheduleTime;
    public Timestamp scheduleDateTime;
    public String scheduleName;
    public String scheduleDescription;
    public Timestamp createDate;
    public Timestamp updateDate;
}
