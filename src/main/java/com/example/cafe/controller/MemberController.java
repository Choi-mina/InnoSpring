package com.example.cafe.controller;

import com.example.cafe.Service.CommentsService;
import com.example.cafe.Service.CommunityService;
import com.example.cafe.Service.MemberService;
import com.example.cafe.dto.CommentsDto;
import com.example.cafe.dto.CommunityDto;
import com.example.cafe.dto.MemberDto;
import com.example.cafe.entity.ApiResult;
import com.example.cafe.entity.Member;
import com.example.cafe.entity.ResultEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final CommunityService communityService;
    private final CommentsService commentsService;

    @PostMapping("/sign-up")
    public ResultEntity memSignUp(@RequestBody MemberDto memberDto) {
        ResultEntity result = new ResultEntity();

        // 1 -> "F", 2 -> "A", 3 -> "M"
        // 기본값 : 1
        memberDto.setFlag(memberDto.getFlag().equals("1") ? "F" : memberDto.getFlag().equals("2") ? "A" : "M" );
        try{
            if(!memValid(memberDto.getEmail())) {
                memberService.memSignUp(memberDto);

                // 회원가입 성공 반환
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

    @GetMapping("/login-in")
    public ResultEntity logIn(@RequestParam("email") String email, @RequestParam("password") String password) {
        ResultEntity result = new ResultEntity<ApiResult>();
        try{
            // email로 회원 정보 조회
            MemberDto member = memberService.findByEmail(email);

            if(member != null) {    // 존재하는 회원
                if(member.getPassword().equals(password)) { // email과 password가 일치 -> 로그인 성공
                    result.setCode(ApiResult.SUCCESSS.getCode());
                    result.setMessage(ApiResult.SUCCESSS.getMessage());
                    result.setData(member);
                } else {    // 로그인 실패
                    result.setCode(ApiResult.FAIL.getCode());
                    result.setMessage(ApiResult.FAIL.getMessage());
                }
            } else {    // 존재하는 회원이 없는 경우
                result.setCode(ApiResult.USER_NOT_FOUND.getCode());
                result.setMessage(ApiResult.USER_NOT_FOUND.getMessage());
                log.info("Log-in >>>>> Not Found User");
            }
            log.info("Log-in >>>>> Success");
        }catch (Exception e) {
            log.error("Log-in >>>>> Fail");
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
            log.error("Find By Email Fail");
            return new ResultEntity(e);
        }
        return result;
    }

    @PostMapping("/modify")
    public ResultEntity modifyMember(@RequestBody MemberDto memberDto) {
        ResultEntity result = new ResultEntity();
        try {
            memberService.modifyMember(memberDto);

            result.setCode("0000");
            result.setMessage("Member Modify Success");

            log.info("Member Modify Success");
        } catch (Exception e) {
            log.error("Member Modify Fail");
            return new ResultEntity(e);
        }
        return result;
    }

    @PostMapping("/delete")
    public ResultEntity deleteMember(@RequestParam("email") String email) {
        ResultEntity result = new ResultEntity();
        try {
            MemberDto member = memberService.findByEmail(email);
            memberService.deleteMember((long) member.getMemId());

            result.setCode("0000");
            result.setMessage("Member Delete Success");

            log.info("Member Delete Success");
        } catch (Exception e) {
            log.error("Member Delete Fail");
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
