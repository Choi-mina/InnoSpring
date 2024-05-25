package com.example.cafe.controller;

import com.example.cafe.dto.MemberDto;
import com.example.cafe.entity.ResultEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberController {

    @PostMapping("/sign-up")
    public ResultEntity memLogin(@RequestBody MemberDto memberDto) {
        ResultEntity result = new ResultEntity();

        return result;
    }
}
