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
        sessionValues.put("flag", session.getAttribute("flag"));
        sessionValues.put("date", session.getAttribute("date"));
        return ResponseEntity.ok(sessionValues);
    }

    @RequestMapping("/login-web")
    public String LoginWeb() {
        return "html/login.html";
    }

    @RequestMapping("/sign-up-web")
    public String SignUpWeb(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "html/signup.html";
    }

    @RequestMapping("/mypage-web")
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
}
