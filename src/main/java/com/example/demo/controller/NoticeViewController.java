package com.example.demo.controller;

import com.example.demo.entity.Notice;
import com.example.demo.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Controller // 🌟 중요: @RestController가 아니라 @Controller 입니다! (HTML 화면을 반환함)
@RequiredArgsConstructor
public class NoticeViewController {

    private final NoticeRepository noticeRepository;

    @GetMapping("/board") // 인터넷 주소창에 /board 라고 치면 실행됨
    public String showBoard(Model model) {
        // 1. DB에서 공지사항 데이터를 전부 다 가져옵니다.
        List<Notice> noticeList = noticeRepository.findAll();

        // 2. 가져온 데이터에 "notices"라는 이름표를 붙여서 화면(HTML)으로 넘겨줍니다.
        model.addAttribute("notices", noticeList);

        // 3. templates 폴더 안에 있는 "board.html" 파일을 찾아서 브라우저에 띄워라!
        return "board";
    }

    // ... (기존에 있던 @GetMapping("/board") 코드는 그대로 둡니다) ...

    // 1. 글쓰기 버튼을 누르면 -> 글쓰기 화면(write.html)을 띄워준다!
    @GetMapping("/write")
    public String showWriteForm() {
        return "write";
    }

    @GetMapping("/delete/{id}") // 주소에 포함된 {id} 값을 가져옵니다.
    public String deleteNotice(@PathVariable("id") Long id) {
        // 1. 해당 ID의 글을 DB에서 삭제하라!
        noticeRepository.deleteById(id);

        // 2. 삭제가 끝났으면 다시 목록(/board)으로 돌아가라!
        return "redirect:/board";
    }

    // 1. 수정 화면 띄우기 (기존 데이터 채워서)
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        // DB에서 해당 글을 찾아와서 화면에 던져줍니다.
        Notice notice = noticeRepository.findById(id).orElse(null);
        model.addAttribute("notice", notice);
        return "edit";
    }

    // 2. 수정 내용 저장하기
    @PostMapping("/edit/{id}")
    public String updateNotice(@PathVariable("id") Long id, Notice notice) {
        // JPA의 save()는 ID가 있으면 '수정(Update)', 없으면 '등록(Insert)'을 알아서 판단합니다!
        noticeRepository.save(notice);
        return "redirect:/board";
    }

    @PostMapping("/write")
    public String saveNotice(Notice notice, MultipartFile file) throws Exception {

        if (!file.isEmpty()) {
            // 1. 파일을 저장할 경로 설정 (예: 프로젝트 루트의 files 폴더)
            String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";

            // 2. 파일 이름 생성 (중복 방지를 위해 UUID를 쓰기도 하지만 일단 원본 이름으로!)
            String fileName = file.getOriginalFilename();

            // 3. 해당 경로에 파일 객체 생성 및 저장
            File saveFile = new File(projectPath, fileName);
            file.transferTo(saveFile);

            // 4. DB에 파일 정보 저장
            notice.setFileName(fileName);
            notice.setFilePath("/files/" + fileName);
        }

        noticeRepository.save(notice);
        return "redirect:/board";
    }
}