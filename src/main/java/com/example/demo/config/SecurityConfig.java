package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/intro", "/css/**", "/js/**").permitAll() // 인트로는 누구나
                        .anyRequest().authenticated() // 나머지는 로그인 필수
                )
                .formLogin(form -> form
                        .loginPage("/intro") // 로그인 화면 경로
                        .loginProcessingUrl("/login") // form action 경로
                        .defaultSuccessUrl("/map", true) // 로그인 성공 시 지도 페이지로!
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/intro")
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}