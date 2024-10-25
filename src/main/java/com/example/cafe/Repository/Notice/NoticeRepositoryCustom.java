package com.example.cafe.Repository.Notice;

import com.example.cafe.dto.NoticeDto;
import com.example.cafe.entity.Notice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface NoticeRepositoryCustom {
    public Page<NoticeDto> findAllNotice(Pageable pageable);
    public Page<NoticeDto> findByTitle(String noticeTitle, Pageable pageable);
    public Page<NoticeDto> findByDate(LocalDate startDate, LocalDate endDate, Pageable pageable);
}
