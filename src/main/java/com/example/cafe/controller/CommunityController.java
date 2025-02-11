package com.example.cafe.controller;

import com.example.cafe.Service.CommunityService;
import com.example.cafe.Service.MemberService;
import com.example.cafe.dto.CommunityDto;
import com.example.cafe.dto.MemberDto;
import com.example.cafe.entity.Member;
import com.example.cafe.entity.ResultEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/community")
public class CommunityController {
    private final CommunityService communityService;
    private final MemberService memberService;

    @PostMapping("/save")
    public ResultEntity saveCommunity(@RequestBody CommunityDto communityDto, @AuthenticationPrincipal MemberDto memberDto) {
        ResultEntity result = new ResultEntity();
        try {
            // 인증된 사용자를 작성자로 넣기
            // MemberDto -> Member
            ModelMapper modelMapper = new ModelMapper();
            Member member = modelMapper.map(memberDto, Member.class);
            communityDto.setCommunityAuthor(member);
            communityService.saveCommunity(communityDto);

            // community save success
            result.setCode("0000");
            result.setMessage("Community Save Success");
            log.info("Community Save Success");
        } catch (Exception e) {
            log.error("Community-save Fail");
            return new ResultEntity(e);
        }

        return result;
    }

    @GetMapping("/find-all")
    public ResultEntity findAllCommunity(Pageable pageable) {
        ResultEntity result = new ResultEntity();
        try {
            Page<CommunityDto> communityDtos = communityService.findAllCommunity(pageable);

            // community findAll success
            result.setCode("0000");
            result.setMessage("Community findAll Success");
            result.setData(communityDtos);
            log.info("Community findAll Success");
        } catch (Exception e) {
            log.error("Community-findAll Fail");
            return new ResultEntity(e);
        }

        return result;
    }

    @GetMapping("/find-by-id")
    public ResultEntity findByIdCommunity(@RequestParam("id") Integer communityId) {
        ResultEntity result = new ResultEntity();
        try {
            CommunityDto communityDto = communityService.findByIdCommunity(communityId);

            // community findByID success
            result.setCode("0000");
            result.setMessage("Community findByID Success");
            result.setData(communityDto);
            log.info("Community findByID Success");
        } catch (Exception e) {
            log.error("Community-findByID Fail");
            return new ResultEntity(e);
        }

        return result;
    }

    // 내 글 조회 API
    @GetMapping("/find-my-community")
    public ResultEntity findMyCommunity(@RequestParam("email") String email, Pageable pageable) {
        ResultEntity result = new ResultEntity();
        try {
            Member member = memberService.findByEmailEt(email);
            Page<CommunityDto> communityDtos = communityService.findByMemberId(member,pageable);

            result.setCode("0000");
            result.setMessage("Find My Community Success");
            result.setData(communityDtos);

            log.info("Find My Community Success");
        } catch (Exception e) {
            log.error("Find My Community Fail");
            return new ResultEntity(e);
        }
        return result;
    }

    @PostMapping("/modify")
    public ResultEntity modifyCommunity(@RequestBody CommunityDto communityDto, @AuthenticationPrincipal MemberDto memberDto) {
        ResultEntity result = new ResultEntity();
        try {
            // 인증된 사용자를 작성자로 넣기
            // MemberDto -> Member
            ModelMapper modelMapper = new ModelMapper();
            Member member = modelMapper.map(memberDto, Member.class);
            communityDto.setCommunityAuthor(member);
            communityService.modifyCommunity(communityDto);

            // community modify success
            result.setCode("0000");
            result.setMessage("Community modify Success");
            log.info("Community modify Success");
        } catch (Exception e) {
            log.error("Community-modify Fail");
            return new ResultEntity(e);
        }

        return result;
    }

    @PostMapping("/delete")
    public ResultEntity deleteCommunity(@RequestParam("id") Integer communityId) {
        ResultEntity result = new ResultEntity();
        try {
            communityService.deleteCommunity(communityId);

            // community delete success
            result.setCode("0000");
            result.setMessage("Community delete Success");
            log.info("Community delete Success");
        } catch (Exception e) {
            log.error("Community-delete Fail");
            return new ResultEntity(e);
        }

        return result;
    }
}
