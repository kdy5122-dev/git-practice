package com.example.demo.config;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.findByLoginId("admin").isEmpty()) {
            User admin = new User();
            admin.setLoginId("admin");
            // 중요: 비밀번호는 반드시 암호화(encode)해서 저장해야 시큐리티가 인식합니다!
            admin.setPassword(passwordEncoder.encode("1234"));
            admin.setUserName("관리자");
            userRepository.save(admin);
            System.out.println("테스트 계정 생성 완료: admin / 1234");
        }
    }
}