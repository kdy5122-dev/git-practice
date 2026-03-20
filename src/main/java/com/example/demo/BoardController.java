package com.example.demo;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardController {
    @GetMapping("/board")
    public String getBoard() {
        return "게시판 데이터입니다!";
    } // 테스트
}
