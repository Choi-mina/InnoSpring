package com.example.cafe.security;

import com.example.cafe.Service.MemberService;
import com.example.cafe.Service.UserService;
import com.example.cafe.entity.MemberContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component("authenticationProvider")
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;
    private final UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        MemberContext memberContext = (MemberContext) userDetailsService.loadUserByUsername(email);

        if(!memberContext.getPassword().equals(password)){
            throw new BadCredentialsException("Invalid password");
        }

        try {
            // Redis에 로그인 정보 저장
            userService.saveUserData(email, password, memberContext.getMemberDto().flag, String.valueOf(memberContext.getMemberDto().getCreateDate()));
            // 세션에 사용자 정보 저장
            session.setAttribute("email", email);
            session.setAttribute("date", memberContext.getMemberDto().getCreateDate());
            session.setAttribute("flag", memberContext.getMemberDto().flag);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new UsernamePasswordAuthenticationToken(memberContext.getMemberDto(), null, memberContext.getAuthorities());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
