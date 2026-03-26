package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 사용자가 입력한 loginId로 DB를 뒤집니다.
    Optional<User> findByLoginId(String loginId);
}
