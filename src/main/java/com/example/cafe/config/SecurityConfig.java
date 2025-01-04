package com.example.cafe.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                        .requestMatchers("/").permitAll()
                        .anyRequest().authenticated()
                )
//                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer.sessionFixation().migrateSession()
//                )
//                .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 비활성화
////                .authorizeHttpRequests(authorize -> authorize
////                        .requestMatchers("/", "/logout", "/redis/**").permitAll() // 접근 허용
////                        .anyRequest().authenticated() // 나머지 요청은 인증 필요
////                )
                .formLogin(form -> form
                        .loginPage("/login-web").permitAll()
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")         // username 필드를 email로 매핑
                        .passwordParameter("userPassword")  // password 필드를 userPassword로 매핑
                        .defaultSuccessUrl("/", true) // 로그인 성공 후 리다이렉트 URL
                )
                .authenticationProvider(authenticationProvider)
                .logout(AbstractHttpConfigurer::disable)
        ;

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 패스워드 암호화
    }
}
