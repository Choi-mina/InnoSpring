package com.example.cafe.controller;

import com.example.cafe.Service.MemberService;
import com.example.cafe.dto.MemberDto;
import com.example.cafe.entity.ResultEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Slf4j
@Controller
public class LoginController {
    @Value("${server.host.api}")
    private String baseUrl;

    private final MemberService memberService;
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public LoginController(MemberService memberService, RedisTemplate<String, String> redisTemplate) {
        this.memberService = memberService;
        this.redisTemplate = redisTemplate;
    }

    @RequestMapping("/log-in-result")
    @ResponseBody
    public ResultEntity logIn(@RequestParam("email") String email, @RequestParam("password") String password, final HttpServletRequest httpRequest) {
        String url = baseUrl + "/member/login-in?email=" + email + "&password=" + password;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MemberDto> httpEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ResultEntity> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity,
                ResultEntity.class
        );

        if (response.getBody().getCode().equals("0000")) {
            final HttpSession session = httpRequest.getSession();
            session.setAttribute("email", email);
            session.setMaxInactiveInterval(3600);

            ValueOperations<String, String> vop = redisTemplate.opsForValue();
            vop.set("user:login:" + email, "true");
        }

        return response.getBody();
    }

    @PostMapping("/logout")
    @ResponseBody
    public ResultEntity logOut(@RequestParam("email") String email, final HttpServletRequest httpRequest) {
        final HttpSession session = httpRequest.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        redisTemplate.delete("user:login:" + email);

        return new ResultEntity("0000", "Logout successful", null);
    }
}
