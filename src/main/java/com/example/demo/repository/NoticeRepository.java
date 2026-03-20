package com.example.demo.repository;

import com.example.demo.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository를 상속받으면, 스프링이 알아서 insert, select 쿼리를 다 만들어줍니다!
public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
