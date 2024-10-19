package com.example.cafe.controller;

import com.example.cafe.Service.CommunityService;
import com.example.cafe.dto.CommunityDto;
import com.example.cafe.entity.ResultEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/community")
public class CommunityController {
    private final CommunityService communityService;

    @PostMapping("/save")
    public ResultEntity saveCommunity(@RequestBody CommunityDto communityDto) {
        ResultEntity result = new ResultEntity();
        try {
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
    public ResultEntity findAllCommunity() {
        ResultEntity result = new ResultEntity();
        try {
            List<CommunityDto> communityDtos = communityService.findAllCommunity();

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
}
