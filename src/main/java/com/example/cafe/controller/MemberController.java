package com.example.cafe.controller;

import com.example.cafe.Service.MemberService;
import com.example.cafe.dto.MemberDto;
import com.example.cafe.entity.Member;
import com.example.cafe.entity.ResultEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
            if(!memValid(memberDto.getEmail())) {
                memberService.memSignUp(memberDto);

                // 회원가입 및 로그인 성공 반환
                result.setCode("0000");
                result.setMessage("Sign-up Success");
                log.info("Sign-up Success");
            } else{
                // 이미 존재하는 회원
                result.setCode("0001");
                result.setMessage(memberDto.getEmail() + " exist");
                log.info("Sign-up Fail");
            }
        }catch (Exception e) {
            log.error("Sign-up Fail");
            return new ResultEntity(e);
        }
        return result;
    }

    @GetMapping("/find-by-phone")
    public ResultEntity findByPhone(@RequestParam("phoneNum") String phoneNum) {
        ResultEntity result = new ResultEntity();
        try {
            MemberDto member = memberService.findByPhoneNum(phoneNum);

            if(member != null) {
                result.setCode("0000");
                result.setMessage("Find By Phone Success");
                result.setData(member);
            } else {
                result.setCode("0001");
                result.setMessage("No Data");
            }
            log.info("Find By Phone Success");
        } catch (Exception e) {
            log.error("Find By Phone Fail");
            return new ResultEntity(e);
        }
        return result;
    }

    @GetMapping("/find-by-email")
    public ResultEntity findByEmail(@RequestParam("email") String email) {
        ResultEntity result = new ResultEntity();
        try {
            MemberDto member = memberService.findByEmail(email);

            if(member != null) {
                result.setCode("0000");
                result.setMessage("Find By Email Success");
                result.setData(member);
            } else {
                result.setCode("0001");
                result.setMessage("No Data");
            }
            log.info("Find By Email Success");
        } catch (Exception e) {
            log.error("Find By Phone Fail");
            return new ResultEntity(e);
        }
        return result;
    }



    // 회원이 존재하는지 validation
    public Boolean memValid(String email) {
        log.info("Find Member");
        MemberDto member = memberService.findByEmail(email);
        if(member != null)
            return true;
        return false;
    }
}
