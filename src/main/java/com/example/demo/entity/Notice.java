package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity // 1. "JPA야, 이 클래스를 바탕으로 DB에 테이블을 만들어줘!"
@Getter // 2. Lombok: "getTitle(), getId() 같은 메서드 알아서 만들어줄게"
@Setter // 3. Lombok: "setTitle(), setId() 같은 메서드 알아서 만들어줄게"
public class Notice {

    @Id // 4. "이 변수가 테이블의 Primary Key(기본키)야!"
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 5. "ID 번호는 DB가 알아서 1, 2, 3... 순서대로 늘려줘!"
    private Long id;

    private String title;
    private String content;
    // Notice.java 파일에 추가
    private String fileName; // 실제 저장된 파일 이름
    private String filePath; // 파일이 저장된 경로

}
