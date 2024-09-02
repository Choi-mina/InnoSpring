package com.example.cafe.controller;

import com.example.cafe.dto.MemberDto;
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
        String cachedData = ops.get("user:login:" + email);

        return "html/home.html";
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

    // 로그인을 위한 Web APi
//    @RequestMapping("/log-in-result")
//    @ResponseBody
//    public ResultEntity LogIn(@RequestParam("email") String email, @RequestParam("password") String password, final HttpServletRequest httpRequest) {
//        String url = baseUrl + "/member/login-in?email=" + email + "&password=" + password;
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<MemberDto> httpEntity = new HttpEntity<>(headers);
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<ResultEntity> response = restTemplate.exchange(
//                url,
//                HttpMethod.GET,
//                httpEntity,
//                ResultEntity.class);
//
//        final HttpSession session = httpRequest.getSession();
//        session.setAttribute("email", email);
//        session.setMaxInactiveInterval(3600);
//
//        if(response.getBody().getCode().equals("0000")) {
////            String RedisUrl = baseUrl + "/redis/set";
////            HttpHeaders RedisHeaders = new HttpHeaders();
////            headers.setContentType(MediaType.APPLICATION_JSON);
////            HttpEntity<MemberDto> RedisHttpEntity = new HttpEntity<>(headers);
////            RestTemplate RedisRestTemplate = new RestTemplate();
////            ResponseEntity<ResultEntity> RedisResponse = restTemplate.exchange(
////                    url,
////                    HttpMethod.POST,
////                    httpEntity,
////                    ResultEntity.class);
//        }
//        return response.getBody();
//    }
}
