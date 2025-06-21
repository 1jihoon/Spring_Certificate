package com.example.spring_certificate.Dto.CommunityDto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CommunityDto {
    private Long id;
    private String title;
    private String writer;
    private String content;
    private String departmentId;
    private String majorId;
    private LocalDateTime createdAt; // ✅ 추가: 날짜 표시용
    private int viewCount;

    // 업로드용 파일들
    private List<MultipartFile> newImages;

    // 삭제할 이미지 ID
    private List<Long> deleteImageIds;

    // 조회용 기존 이미지 목록
    private List<CommunityImageDto> existingImages;
}