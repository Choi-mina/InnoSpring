package com.example.cafe.controller;

import com.example.cafe.Service.NoticeService;
import com.example.cafe.dto.NoticeDto;
import com.example.cafe.entity.ResultEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResultEntity findAllNotice() {
        ResultEntity result = new ResultEntity();
        try {
            List<NoticeDto> noticeDtoList = noticeService.getAllNotices();
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
}
