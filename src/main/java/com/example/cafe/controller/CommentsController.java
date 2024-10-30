package com.example.cafe.controller;

import com.example.cafe.Service.ArtistService;
import com.example.cafe.Service.CommentsService;
import com.example.cafe.Service.CommunityService;
import com.example.cafe.Service.MemberService;
import com.example.cafe.dto.ArtistDto;
import com.example.cafe.dto.CommentsDto;
import com.example.cafe.dto.CommunityDto;
import com.example.cafe.entity.Member;
import com.example.cafe.entity.ResultEntity;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/comments")
public class CommentsController {
    private final CommentsService commentsService;
    private final MemberService memberService;
    private final CommunityService communityService;
    private final ArtistService artistService;

    @PostMapping("/save")
    public ResultEntity save(@RequestBody CommentsDto commentsDto) {
        ResultEntity result = new ResultEntity();
        try {
            Member member = memberService.findByEmailEt(commentsDto.getAuthor());
            commentsDto.setCommentsAuthor(member);
            if(commentsDto.getFlag().equals("C")) { // Community 글인 경우
                CommunityDto communityDto = communityService.findByIdCommunity(commentsDto.getCommunityId());
                commentsDto.setParentCpost(communityDto);
            } else {    // Artist 글인 경우
                ArtistDto artistDto = artistService.getArtistById(commentsDto.getArtistId());
                commentsDto.setParentApost(artistDto);
            }

            commentsService.saveComments(commentsDto);

            // Comments save success
            result.setCode("0000");
            result.setMessage("Comments Save Success");
            log.info("Comments Save Success");
        } catch (Exception e) {
            log.error("Comments-save Fail");
            return new ResultEntity(e);
        }

        return result;
    }
}
