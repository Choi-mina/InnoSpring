package com.example.cafe.Service;

import com.example.cafe.Repository.NoticeRepository;
import com.example.cafe.dto.NoticeDto;
import com.example.cafe.dto.ScheduleDto;
import com.example.cafe.entity.Notice;
import com.example.cafe.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public List<NoticeDto> getAllNotices() {
        List<Notice> notices = noticeRepository.findAll();
        List<NoticeDto> noticeDtoList = new ArrayList<>();

        if(notices != null)
            // 내림차순으로 Entity -> Dto
            for(int i = notices.size() - 1; i >= 0; i--) {
                NoticeDto dto = NoticeDto.builder()
                        .noticeId(notices.get(i).getNoticeId())
                        .noticeTitle(notices.get(i).getNoticeTitle())
                        .noticeContent(notices.get(i).getNoticeContent())
                        .createDate(notices.get(i).getCreateDate())
                        .updateDate(notices.get(i).getUpdateDate())
                        .build();

                noticeDtoList.add(dto);  // 리스트에 추가
            }

        return noticeDtoList;
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

    public List<NoticeDto> findByTitle(String title) {
        List<Notice> notices = noticeRepository.findByTitle(title);
        List<NoticeDto> noticeDtoList = new ArrayList<>();

        if(notices != null)
            // 내림차순으로 Entity -> Dto
            for(int i = notices.size() - 1; i >= 0; i--) {
                NoticeDto dto = NoticeDto.builder()
                        .noticeId(notices.get(i).getNoticeId())
                        .noticeTitle(notices.get(i).getNoticeTitle())
                        .noticeContent(notices.get(i).getNoticeContent())
                        .createDate(notices.get(i).getCreateDate())
                        .updateDate(notices.get(i).getUpdateDate())
                        .build();

                noticeDtoList.add(dto);  // 리스트에 추가
            }

        return noticeDtoList;
    }

    public List<NoticeDto> findByDate(String date1, String date2) {
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

        List<Notice> notices = noticeRepository.findByDate(startDate, endDate);
        List<NoticeDto> noticeDtoList = new ArrayList<>();

        if(notices != null)
            // 내림차순으로 Entity -> Dto
            for(int i = notices.size() - 1; i >= 0; i--) {
                NoticeDto dto = NoticeDto.builder()
                        .noticeId(notices.get(i).getNoticeId())
                        .noticeTitle(notices.get(i).getNoticeTitle())
                        .noticeContent(notices.get(i).getNoticeContent())
                        .createDate(notices.get(i).getCreateDate())
                        .updateDate(notices.get(i).getUpdateDate())
                        .build();

                noticeDtoList.add(dto);  // 리스트에 추가
            }

        return noticeDtoList;
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
