package com.example.cafe.controller;

import com.example.cafe.Service.MemberService;
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
    private final MemberService memberService;

    @PostMapping("/sign-up")
    public ResultEntity memSignUp(@RequestBody MemberDto memberDto) {
        ResultEntity result = new ResultEntity();
        try{
            memberService.memSignUp(memberDto);

            // 회원가입 및 로그인 성공 반환
            result.setCode("0000");
            result.setMessage("Sign-up Success");
            log.info("Sign-up Success");
        }catch (Exception e) {
            log.error("Sign-up Fail");
            return new ResultEntity(e);
        }
        return result;
    }
}
