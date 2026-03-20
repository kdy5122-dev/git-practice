package com.example.demo.controller;

import com.example.demo.entity.Notice;
import com.example.demo.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // 화면 대신 데이터를 바로 보여주는 전용 컨트롤러
@RequiredArgsConstructor // 롬복 기능: 아래 NoticeRepository를 알아서 연결해줌
public class NoticeController {

    private final NoticeRepository noticeRepository;

    // 1. 글쓰기 기능 (테스트용)
    // 주소창에 http://localhost:8080/add 라고 치면 실행됨!
    @GetMapping("/add")
    public String addNotice() {
        Notice notice = new Notice();
        notice.setTitle("첫 번째 공지사항입니다! 🎉");
        notice.setContent("드디어 DB에 데이터가 들어갑니다. 감격스럽네요!");

        noticeRepository.save(notice); // DB에 저장 (Insert 쿼리 자동 실행)

        return "게시글이 성공적으로 등록되었습니다! DB를 확인해보세요.";
    }

    // 2. 목록 보기 기능
    // 주소창에 http://localhost:8080/list 라고 치면 실행됨!
    @GetMapping("/list")
    public List<Notice> getNoticeList() {
        // DB에 있는 모든 공지사항을 다 가져와서 보여줌 (Select 쿼리 자동 실행)
        return noticeRepository.findAll();
    }
}