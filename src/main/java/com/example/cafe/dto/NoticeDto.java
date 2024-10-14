package com.example.cafe.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDto {
    private String noticeTitle;
    private String noticeContent;
    public Timestamp createDate;
    public Timestamp updateDate;
}
