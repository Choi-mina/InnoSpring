package com.example.cafe.controller;

import com.example.cafe.dto.MemberDto;
import com.example.cafe.entity.ResultEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
}
