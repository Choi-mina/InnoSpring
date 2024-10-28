package com.example.cafe.controller;

import com.example.cafe.Service.NoticeService;
import com.example.cafe.dto.NoticeDto;
import com.example.cafe.entity.ResultEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeService noticeService;

    @PostMapping("/save")
    public ResultEntity saveNotice(@RequestBody NoticeDto notice) {
        ResultEntity result = new ResultEntity();
        try {
            noticeService.saveNotice(notice);
            // Notice save success
            result.setCode("0000");
            result.setMessage("Notice Save Success");
            log.info("Notice Save Success");
        } catch (Exception e) {
            log.error("Notice-save Fail");
            return new ResultEntity(e);
        }

        return result;
    }

    @GetMapping("/find-all")
    public ResultEntity findAllNotice(Pageable pageable) {
        ResultEntity result = new ResultEntity();
        try {
            Page<NoticeDto> noticeDtoList = noticeService.getAllNotices(pageable);
            // Find All Notice success
            result.setCode("0000");
            result.setMessage("Find All Notice Success");
            result.setData(noticeDtoList);
            log.info("Find All Notice Success");
        }   catch (Exception e) {
            log.error("Find All Notice Fail");
            return new ResultEntity(e);
        }
        return result;
    }

    @GetMapping("/find-by-id")
    public ResultEntity findNoticeById(@RequestParam("id") int noticeId) {
        ResultEntity result = new ResultEntity();
        try {
            NoticeDto noticeDto = noticeService.getNoticeById(noticeId);
            // Find All Notice Success
            result.setCode("0000");
            result.setMessage("Find Notice Success");
            result.setData(noticeDto);
            log.info("Find Notice Success");
        }   catch (Exception e) {
            log.error("Find Notice Fail");
            return new ResultEntity(e);
        }
        return result;
    }

    @GetMapping("/find-by-title")
    public ResultEntity findByTitle(@RequestParam String title, Pageable pageable) {
        ResultEntity result = new ResultEntity();
        try {
            Page<NoticeDto> noticeDtoList = noticeService.findByTitle(title, pageable);
            // Find By Title Notice Success
            result.setCode("0000");
            result.setMessage("Find By Title Notice Success");
            result.setData(noticeDtoList);
            log.info("Find By Title Notice Success");
        }   catch (Exception e) {
            log.error("Find By Title Notice Fail");
            return new ResultEntity(e);
        }
        return result;
    }

    @GetMapping("/find-by-date")
    public ResultEntity findByDate(@RequestParam String date1, @RequestParam String date2, Pageable pageable) {
        ResultEntity result = new ResultEntity();
        try {
            Page<NoticeDto> noticeDtoList = noticeService.findByDate(date1, date2, pageable);
            // Find By Date Notice Success
            result.setCode("0000");
            result.setMessage("Find By Date Notice Success");
            result.setData(noticeDtoList);
            log.info("Find By Date Notice Success");
        }   catch (Exception e) {
            log.error("Find By Date Notice Fail");
            return new ResultEntity(e);
        }
        return result;
    }

    @PostMapping("/modify")
    public ResultEntity modifyNotice(@RequestBody NoticeDto notice) {
        ResultEntity result = new ResultEntity();
        try {
            noticeService.modifyNotice(notice);
            result.setCode("0000");
            result.setMessage("Notice Modify Success");
            log.info("Notice Modify Success");
        }   catch (Exception e) {
            log.error("Notice Modify Fail");
            return new ResultEntity(e);
        }
        return result;
    }

    @PostMapping("/delete")
    public ResultEntity deleteNotice(@RequestParam("id") int noticeId) {
        ResultEntity result = new ResultEntity();
        try {
            noticeService.deleteNotice(noticeId);
            result.setCode("0000");
            result.setMessage("Notice Delete Success");
            log.info("Notice Delete Success");
        }   catch (Exception e) {
            log.error("Notice Delete Fail");
            return new ResultEntity(e);
        }
        return result;
    }
}
