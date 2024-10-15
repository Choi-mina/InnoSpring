package com.example.cafe.Repository;

import com.example.cafe.entity.Notice;

import java.time.LocalDate;
import java.util.List;

public interface NoticeRepositoryCustom {
    public List<Notice> findByTitle(String noticeTitle);
    public List<Notice> findByDate(LocalDate startDate, LocalDate endDate);
}
