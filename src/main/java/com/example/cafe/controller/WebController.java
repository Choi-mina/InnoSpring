package com.example.cafe.controller;

import com.example.cafe.dto.MemberDto;
import com.example.cafe.dto.NoticeDto;
import com.example.cafe.dto.ScheduleDto;
import com.example.cafe.entity.ResultEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.Result;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WebController {
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${server.host.api}")
    private String baseUrl;

    @RequestMapping("/")
    public String MainWeb() {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        // redis에서 key에 해당하는 value를 찾음.
//        String cachedData = ops.get("user:login:" + email);

        return "html/home.html";
    }

    @RequestMapping("/get-session")
    public ResponseEntity<Map<String, Object>> getSessionValues(HttpSession session) {

        Map<String, Object> sessionValues = new HashMap<>();
        sessionValues.put("email", session.getAttribute("email"));
        return ResponseEntity.ok(sessionValues);
    }

    @RequestMapping("/login")
    public String LoginWeb() {
        return "html/login.html";
    }

    @RequestMapping("/sign-up")
    public String SignUpWeb(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "html/signup.html";
    }

    @RequestMapping("/mypage")
    public String MyPageWeb(Model model) {
        return "html/mypage.html";
    }

    @RequestMapping("/community-web")
    public String CommunityWeb(Model model) {
        return "html/community.html";
    }

    @RequestMapping("artist-web")
    public String ArtistWeb(Model model) {
        return "html/artist.html";
    }

    @RequestMapping("schedule-web")
    public String ScheduleWeb(Model model) {
        return "html/schedule.html";
    }

    @RequestMapping("/notice-web")
    public String InformWeb(Model model) {
        return "html/notice.html";
    }

    // 이메일 중복확인을 위한 Web API
    @RequestMapping("/find-email")
    @ResponseBody
    public ResultEntity FindByEmail(@RequestParam("email") String email) {
        String url = baseUrl + "/member/find-by-email?email=" + email;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MemberDto> httpEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ResultEntity> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity,
                ResultEntity.class);
        return response.getBody();
    }

    // 회원가입을 위한 Web API
    @RequestMapping("/sign-up-result")
    @ResponseBody
    public ResultEntity SginUp(@RequestBody MemberDto memberDto) {
        String url = baseUrl + "/member/sign-up";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MemberDto> httpEntity = new HttpEntity<>(memberDto, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ResultEntity> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                httpEntity,
                ResultEntity.class);
        return response.getBody();
    }

    // 스케줄 저장을 위한 Web API
    @RequestMapping("/schedule-save-web")
    @ResponseBody
    public ResultEntity ScheduleSave(@RequestBody ScheduleDto scheduleDto) {
        String url = baseUrl + "/schedule/save";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ScheduleDto> httpEntity = new HttpEntity<>(scheduleDto, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ResultEntity> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                httpEntity,
                ResultEntity.class);
        return response.getBody();
    }

    // 스케줄 조회 Web API
    @RequestMapping("/find-schedule-web")
    @ResponseBody
    public ResultEntity FindSchedule(@RequestParam("date") String date) {
        String url = baseUrl + "/schedule/find-by-date?date=" + date;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MemberDto> httpEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ResultEntity> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity,
                ResultEntity.class);
        return response.getBody();
    }

    // 스케줄 수정 Web API
    @RequestMapping("/modify-schedule-web")
    @ResponseBody
    public ResultEntity ScheduleModify(@RequestBody ScheduleDto scheduleDto) {
        String url = baseUrl + "/schedule/modify";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ScheduleDto> httpEntity = new HttpEntity<>(scheduleDto, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ResultEntity> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                httpEntity,
                ResultEntity.class);
        return response.getBody();
    }

    // 스케줄 삭제 Web API
    @RequestMapping("/delete-schedule-web")
    @ResponseBody
    public ResultEntity DeleteSchedule(@RequestParam("id") int id) {
        String url = baseUrl + "/schedule/delete?id=" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ScheduleDto> httpEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ResultEntity> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                httpEntity,
                ResultEntity.class);
        return response.getBody();
    }

    // 공지사항 저장 Web API
    @RequestMapping("/save-notice-web")
    @ResponseBody
    public ResultEntity SaveNotice(@RequestBody NoticeDto noticeDto) {
        String url = baseUrl + "/notice/save";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<NoticeDto> httpEntity = new HttpEntity<>(noticeDto, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ResultEntity> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                httpEntity,
                ResultEntity.class);
        return response.getBody();
    }

    @RequestMapping("/find-all-notice-web")
    @ResponseBody
    public ResultEntity FindAllNotice() {
        String url = baseUrl + "/notice/find-all";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MemberDto> httpEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ResultEntity> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity,
                ResultEntity.class);
        return response.getBody();
    }
}
