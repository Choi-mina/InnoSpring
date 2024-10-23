package com.example.cafe.controller;

import com.example.cafe.Service.MemberService;
import com.example.cafe.Service.UserService;
import com.example.cafe.dto.MemberDto;
import com.example.cafe.entity.ApiResult;
import com.example.cafe.entity.ResultEntity;
import com.example.cafe.util.EncryptionUtil;
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

import java.sql.Timestamp;
import java.util.LinkedHashMap;

@Slf4j
@Controller
public class LoginController {
    @Value("${server.host.api}")
    private String baseUrl;

    private final MemberService memberService;
    @Autowired
    private UserService userService;
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public LoginController(MemberService memberService, RedisTemplate<String, String> redisTemplate) {
        this.memberService = memberService;
        this.redisTemplate = redisTemplate;
    }

    @RequestMapping("/log-in-result")
    @ResponseBody
    public ResultEntity logIn(@RequestParam("email") String email, @RequestParam("password") String password, final HttpServletRequest httpRequest) throws Exception {
        String LoginPassword = userService.getPassword(email);
        ResultEntity response = new ResultEntity<>();

        if(LoginPassword != null){ // redis에 정보가 있는 경우
            if(LoginPassword.equals(password)){ // 비밀번호가 일치하는 경우 -> 로그인 성공
                String flag = userService.getFlag(email);
                String date = userService.getDate(email);
                response.setCode(ApiResult.SUCCESSS.getCode());
                response.setMessage("Login Success");
                HttpSession session = httpRequest.getSession();
                session.setAttribute("email", email);
                session.setAttribute("flag", flag);
                session.setAttribute("date", date);
                session.setMaxInactiveInterval(3600);
            } else {    // 비밀번호가 일치하지 않는 경우 -> 로그인 실패
                response.setCode(ApiResult.FAIL.getCode());
                response.setMessage("Login Fail");
            }
        } else {    // redis에 정보가 없는 경우 -> DB 조회
            String url = baseUrl + "/member/login-in?email=" + email + "&password=" + password;
            HttpSession session = httpRequest.getSession();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<MemberDto> httpEntity = new HttpEntity<>(headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<ResultEntity> result = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    httpEntity,
                    ResultEntity.class
            );

            String sessionId = session.getId();

            if (result.getBody().getCode().equals("0000")) {    //  일치하는 회원O
                Object resultEntity = result.getBody().getData();
                String flag = (String) ((LinkedHashMap) resultEntity).get("flag");
                String date = (String) ((LinkedHashMap) resultEntity).get("createDate");
                // 세션 저장
                session.setAttribute("email", email);
                session.setAttribute("flag", flag);
                session.setAttribute("date", date);
                session.setMaxInactiveInterval(3600);

                // Redis 저장
                try {
                    userService.saveUserData(email, password, sessionId, flag, date);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                response.setCode(ApiResult.SUCCESSS.getCode());
                response.setMessage("Login Success");
            } else {    // 일치하는 회원X -> 로그인 실패
                response.setCode(ApiResult.FAIL.getCode());
                response.setMessage("Login Fail");
            }
        }
        return response;
    }

    @PostMapping("/logout")
    @ResponseBody
    public ResultEntity logOut(@RequestBody String email, final HttpServletRequest httpRequest) {
        final HttpSession session = httpRequest.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Redis에서 세션 정보 삭제
        try {
            userService.deleteUserData(email);
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("logout success");

        return new ResultEntity("0000", "Logout successful", null);
    }
}
