package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IntroController {

    // 1. 인트로(로그인) 페이지 호출
    @GetMapping("/intro")
    public String intro() {
        return "intro"; // src/main/resources/templates/intro.html을 찾습니다.
    }

    // 2. 로그인 성공 후 이동할 지도 페이지
    @GetMapping("/map")
    public String map() {
        return "map"; // src/main/resources/templates/map.html을 찾습니다.
    }
}