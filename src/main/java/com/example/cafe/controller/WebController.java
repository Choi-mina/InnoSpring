package com.example.cafe.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.example.cafe.dto.MemberDto;
import com.example.cafe.dto.NoticeDto;
import com.example.cafe.dto.ScheduleDto;
import com.example.cafe.entity.ResultEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @RequestMapping("/search-ip-web")
    public String searchIP(Model model) {
        return "html/searchIP.html";
    }

    @GetMapping("/search-ip-result-web")
    public String searchIPResult(@RequestParam("result") String result, Model model) {
        // JSON 데이터를 Java 객체로 변환 (Jackson 라이브러리 사용 가능)
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> resultMap = null;
        try {
            resultMap = objectMapper.readValue(result, new TypeReference<Map<String, Object>>() {});

            // 이메일 마스킹 처리
            String email = (String) resultMap.get("email");
            String[] parts = email.split("@");
            String localPart = parts[0];
            String domainPart = parts[1];
            String maskedLocal = localPart.length() <= 4
                    ? localPart.charAt(0) + "**" + localPart.charAt(localPart.length() - 1)
                    : localPart.substring(0, 2) + "*".repeat(localPart.length() - 4) + localPart.substring(localPart.length() - 2);
            email = maskedLocal + "@" + domainPart;


            // 모델에 데이터 추가
            String type = (String) resultMap.get("type");
            model.addAttribute("type", type);
            model.addAttribute("email", email);
            model.addAttribute("id", resultMap.get("memId"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "html/searchIPResult.html";
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
