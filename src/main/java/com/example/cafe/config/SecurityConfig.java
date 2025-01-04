package com.example.cafe.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/images/**", "/js/**", "/favicon.*", "/*/icon-*").permitAll()
                        .requestMatchers("/", "/sign-up-web").permitAll()
                        .anyRequest().authenticated()
                )
//                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer.sessionFixation().migrateSession()
//                )
//                .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 비활성화
////                .authorizeHttpRequests(authorize -> authorize
////                        .requestMatchers("/", "/logout", "/redis/**").permitAll() // 접근 허용
////                        .anyRequest().authenticated() // 나머지 요청은 인증 필요
////                )
                // 세션 관리 정책 설정: 세션 무효화 강제 설정
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) // 항상 세션을 생성
                        .invalidSessionUrl("/login-web") // 세션이 무효화되면 이 URL로 리다이렉트
                )

                .formLogin(form -> form
                        .loginPage("/login-web").permitAll()
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")         // username 필드를 email로 매핑
                        .passwordParameter("userPassword")  // password 필드를 userPassword로 매핑
                        .defaultSuccessUrl("/", true) // 로그인 성공 후 리다이렉트 URL
                )
                .authenticationProvider(authenticationProvider)
                .logout(AbstractHttpConfigurer::disable)
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
