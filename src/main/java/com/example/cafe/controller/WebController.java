package com.example.cafe.controller;

import com.example.cafe.dto.MemberDto;
import com.example.cafe.entity.ResultEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WebController {
    @Value("${server.host.api}")
    private String baseUrl;

    @RequestMapping("/")
    public String MainWeb() {
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

    @RequestMapping("/sign-up-result")
    public String SginUp(@ModelAttribute MemberDto memberDto, Model model) {
        String url = baseUrl + "/member/up";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity httpEntity = new HttpEntity<>(memberDto, headers);
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<ResultEntity> response = restTemplate.exchange(
//                url,
//                HttpMethod.POST,
//                httpEntity,
//                ResultEntity.class);
        return "html/login.html";
    }
}
