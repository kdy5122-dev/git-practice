package com.example.demo.service;

import com.example.demo.entity.Notice;
import com.example.demo.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service // 🌟 스프링아, 이 클래스를 서비스로 관리해줘!
@RequiredArgsConstructor // 🌟 final 붙은 필드에 생성자를 만들어줘 (의존성 주입)
@Transactional // 🌟 이 클래스 안의 모든 DB 작업은 하나의 세트로 묶어줘 (오류 시 롤백)
public class NoticeService {

    private final NoticeRepository noticeRepository; // 🌟 롬복 덕분에 빨간줄 안 뜸!

    // 1. 전체 목록 조회
    @Transactional(readOnly = true) // 성능 최적화
    public List<Notice> getAllNotices() {
        return noticeRepository.findAll();
    }

    // 2. 단건 조회 (수정 폼 띄울 때 등)
    @Transactional(readOnly = true)
    public Notice getNoticeById(Long id) {
        return noticeRepository.findById(id).orElse(null);
    }

    // 3. 공지사항 등록 및 파일 저장
    public void registerNotice(Notice notice, MultipartFile file) throws Exception {
        // 컨트롤러에 있던 파일 로직이 여기로 이사왔습니다!
        if (file != null && !file.isEmpty()) {
            String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";
            String fileName = file.getOriginalFilename();
            File saveFile = new File(projectPath, fileName);
            file.transferTo(saveFile);

            notice.setFileName(fileName);
            notice.setFilePath("/files/" + fileName);
        }

        noticeRepository.save(notice);
    }

    // 4. 공지사항 삭제
    public void deleteNotice(Long id) {
        noticeRepository.deleteById(id);
    }

    // 4. 공지사항 업데이트
    public void updateNotice(Notice notice, MultipartFile file)  throws Exception {
        // 컨트롤러에 있던 파일 로직이 여기로 이사왔습니다!
        if (file != null && !file.isEmpty()) {
            String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";
            String fileName = file.getOriginalFilename();
            File saveFile = new File(projectPath, fileName);
            file.transferTo(saveFile);

            notice.setFileName(fileName);
            notice.setFilePath("/files/" + fileName);
        }

        noticeRepository.save(notice);
    }
}