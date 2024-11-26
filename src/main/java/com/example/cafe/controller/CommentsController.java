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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/modify")
    public ResultEntity modify(@RequestBody CommentsDto commentsDto) {
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

            commentsService.modifyComments(commentsDto);

            // Comments modify success
            result.setCode("0000");
            result.setMessage("Comments Modify Success");
            log.info("Comments Modify Success");
        } catch (Exception e) {
            log.error("Comments-modify Fail");
            return new ResultEntity(e);
        }

        return result;
    }

    @PostMapping("/delete")
    public ResultEntity delete(@RequestParam("id") Long id) {
        ResultEntity result = new ResultEntity();
        try {
            commentsService.deleteComments(id);

            // Comments delete success
            result.setCode("0000");
            result.setMessage("Comments Delete Success");
            log.info("Comments Delete Success");
        } catch (Exception e) {
            log.error("Comments-delete Fail");
            return new ResultEntity(e);
        }

        return result;
    }

    // 내 댓글 조회 API
    @GetMapping("/find-my-comment")
    public ResultEntity findMyCommment(@RequestParam("email") String email, @RequestParam("type") String postType, Pageable pageable) {
        ResultEntity result = new ResultEntity();
        try {
            Member member = memberService.findByEmailEt(email);
            Page<CommentsDto> commentsDtos = commentsService.findByMemberId(member, postType, pageable);

            result.setCode("0000");
            result.setMessage("Find My Comment Success");
            result.setData(commentsDtos);

            log.info("Find My Comment Success");
        } catch (Exception e) {
            log.error("Find My Comment Fail");
            return new ResultEntity(e);
        }
        return result;
    }

    @GetMapping("find-by-id")
    public ResultEntity findByCommentsId(@RequestParam("id") Long id, @RequestParam("type") String type) {
        ResultEntity result = new ResultEntity();
        try {
            CommentsDto commentsDto = commentsService.findByCommentsId(id, type);
            if(type.equals("C")) {
                CommunityDto communityDto = communityService.findByIdCommunity(commentsDto.getParentCpost().getCommunityId());
                result.setData(communityDto);
            } else {
                ArtistDto artistDto = artistService.getArtistById(commentsDto.getParentApost().getArtistId());
                result.setData(artistDto);
            }

            // Comments findByCommentsId success
            result.setCode("0000");
            result.setMessage("Comments findByCommentsId Success");

            log.info("Comments findByCommentsId Success");
        } catch (Exception e) {
            log.error("Comments-findByCommentsId Fail");
            return new ResultEntity(e);
        }

        return result;
    }
}
