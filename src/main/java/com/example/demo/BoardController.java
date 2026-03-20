package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // "이 클래스는 웹 브라우저의 요청(URL)을 받아서 화면에 응답해주는 역할이야!"
public class BoardController {

    @GetMapping("/") // "인터넷 주소창에 기본 주소(localhost:8080/)로 접속하면 이 부분을 실행해!"
    public String hello() {
        return "🎉 드디어 게시판 서버에 접속 성공했습니다! 🎉";
    }
}
