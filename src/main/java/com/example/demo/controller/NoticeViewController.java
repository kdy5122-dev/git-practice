package com.example.demo.controller;

import com.example.demo.entity.Notice;
import com.example.demo.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor // 🌟 롬복이 NoticeService를 주입하기 위한 생성자를 만들어줍니다.
public class NoticeViewController {

    // 1. 이제 레포지토리가 아니라 '서비스'를 부릅니다.
    private final NoticeService noticeService;

    // 게시판 목록 보기
    @GetMapping("/board")
    public String showBoard(Model model) {
        // 서비스에게 "목록 다 가져와"라고 시킵니다.
        List<Notice> noticeList = noticeService.getAllNotices();
        model.addAttribute("notices", noticeList);
        return "board";
    }

    // 글쓰기 화면 띄우기
    @GetMapping("/write")
    public String showWriteForm() {
        return "write";
    }

    // 새 글 저장하기
    @PostMapping("/write")
    public String saveNotice(Notice notice, MultipartFile file) throws Exception {
        // 복잡한 파일 저장과 DB 저장은 서비스가 알아서 다 합니다.
        noticeService.registerNotice(notice, file);
        return "redirect:/board";
    }

    // 글 삭제하기
    @GetMapping("/delete/{id}")
    public String deleteNotice(@PathVariable("id") Long id) {
        // 서비스에게 "이 ID 삭제해"라고 시킵니다.
        noticeService.deleteNotice(id);
        return "redirect:/board";
    }

    // 수정 화면 띄우기
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        // 서비스에게 "이 ID 글 좀 찾아줘"라고 시킵니다.
        Notice notice = noticeService.getNoticeById(id);
        model.addAttribute("notice", notice);
        return "edit";
    }

    // 수정 내용 저장하기
    @PostMapping("/edit/{id}")
    public String updateNotice(Notice notice, MultipartFile file) throws Exception{
        // 서비스에게 수정을 요청합니다. (파일 없이 업데이트하는 예시)
        noticeService.updateNotice(notice, file);
        return "redirect:/board";
    }
}