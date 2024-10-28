package com.example.cafe.Service;

import com.example.cafe.Repository.Notice.NoticeRepository;
import com.example.cafe.dto.NoticeDto;
import com.example.cafe.entity.Notice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public void saveNotice(NoticeDto noticeDto) {
        Notice notice = new Notice();
        // Dto -> Entity
        notice.setNoticeTitle(noticeDto.getNoticeTitle());
        notice.setNoticeContent(noticeDto.getNoticeContent());
        noticeRepository.save(notice);
    }

    public Page<NoticeDto> getAllNotices(Pageable pageable) {
        return noticeRepository.findAllNotice(pageable);
    }

    public  NoticeDto getNoticeById(int noticeId) {
        Optional<Notice> notice = noticeRepository.findById(Long.valueOf(noticeId));
        NoticeDto noticeDto = new NoticeDto();

        if(notice != null) {
            noticeDto = NoticeDto.builder()
                    .noticeId(notice.get().getNoticeId())
                    .noticeTitle(notice.get().getNoticeTitle())
                    .noticeContent(notice.get().getNoticeContent())
                    .createDate(notice.get().getCreateDate())
                    .updateDate(notice.get().getUpdateDate())
                    .build();
        }

        return noticeDto;
    }

    public Page<NoticeDto> findByTitle(String title, Pageable pageable) {
        return noticeRepository.findByTitle(title, pageable);
    }

    public Page<NoticeDto> findByDate(String date1, String date2, Pageable pageable) {
        // DateTimeFormatter를 사용해 문자열을 LocalDate로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date1LocalDate = LocalDate.parse(date1, formatter);
        LocalDate date2LocalDate = LocalDate.parse(date2, formatter);

        // 날짜 비교 및 startDate, endDate 설정
        LocalDate startDate;
        LocalDate endDate;

        if (date1LocalDate.isBefore(date2LocalDate)) {
            startDate = date1LocalDate;
            endDate = date2LocalDate;
        } else {
            startDate = date2LocalDate;
            endDate = date1LocalDate;
        }

        return noticeRepository.findByDate(startDate, endDate, pageable);
    }

    public void modifyNotice(NoticeDto noticeDto) {
        Notice notice = new Notice();
        notice.setNoticeId(noticeDto.getNoticeId());
        notice.setNoticeTitle(noticeDto.getNoticeTitle());
        notice.setNoticeContent(noticeDto.getNoticeContent());

        noticeRepository.save(notice);
    }

    public void deleteNotice(int noticeId) {
        noticeRepository.deleteById(Long.valueOf(noticeId));
    }
}
