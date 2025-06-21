package com.example.spring_certificate.Service.CommunityService;
// CommunityFileService.java

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class CommunityFileService {

    // 프로젝트 실행 위치 기준으로 uploads 폴더 생성
    private final String uploadDir = System.getProperty("user.dir") + "/uploads/";

    public String storeFile(MultipartFile file) {
        try {
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File dir = new File(uploadDir); // 필드의 uploadDir 사용

            if (!dir.exists()) {
                dir.mkdirs();
            }

            file.transferTo(new File(dir, filename));
            return filename;
        } catch (IOException e) {
            throw new RuntimeException("이미지 파일 저장 실패", e);
        }
    }



    public void deleteFile(String filename) {
        try {
            File file = new File(uploadDir + filename);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            System.err.println("파일 삭제 실패: " + filename);
        }
    }
}
