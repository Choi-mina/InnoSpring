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

import java.util.ArrayList;
import java.util.List;

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
                        .noticeTitle(notices.get(i).getNoticeTitle())
                        .noticeContent(notices.get(i).getNoticeContent())
                        .createDate(notices.get(i).getCreateDate())
                        .build();

                noticeDtoList.add(dto);  // 리스트에 추가
            }

        return noticeDtoList;
    }
}
