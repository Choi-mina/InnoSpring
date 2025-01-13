package com.example.cafe.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationSuccessHandler successHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 요청 경로 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/images/**", "/js/**", "/favicon.*", "/*/icon-*", "/common/**").permitAll()
                        .requestMatchers("/", "/sign-up-web", "/member/**").permitAll()
                        .anyRequest().authenticated()
                )
//                .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 비활성화
////                .authorizeHttpRequests(authorize -> authorize
////                        .requestMatchers("/", "/logout", "/redis/**").permitAll() // 접근 허용
////                        .anyRequest().authenticated() // 나머지 요청은 인증 필요
////                )
                // 세션 관리 설정
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 필요한 경우 세션 생성
                        .sessionFixation().newSession() // 로그인 시 새로운 세션 생성
                        .invalidSessionUrl("/login-web") // 세션이 무효화되면 이 URL로 리다이렉트
                )

                .formLogin(form -> form
                        .loginPage("/login-web").permitAll()
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")         // username 필드를 email로 매핑
                        .passwordParameter("userPassword")  // password 필드를 userPassword로 매핑
                        .successHandler(successHandler) // 로그인 성공 핸들러
                )
                .authenticationProvider(authenticationProvider) // 인증 프로바이더 설정
                // 로그아웃 설정
                .logout(AbstractHttpConfigurer::disable)
                // csrf 설정
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/login", "/login/**", "/member/sign-up") // 특정 URL에 대해 CSRF 예외 처리
                )
        ;

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 패스워드 암호화
    }
}
