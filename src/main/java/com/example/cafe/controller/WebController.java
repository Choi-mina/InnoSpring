package com.example.cafe.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WebController {

    @RequestMapping("/")
    public String MainWeb() {
        return "html/home.html";
    }
}
