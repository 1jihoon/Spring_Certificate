package com.example.spring_certificate.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String writer;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int viewCount;

    private LocalDateTime createdAt;

    private String departmentId; // 소속 학과 정보
    private String majorId;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
