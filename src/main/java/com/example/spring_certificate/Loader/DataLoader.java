package com.example.spring_certificate.Loader;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final FacultyCsvLoader facultyCsvLoader;
    private final DepartmentCsvLoader departmentCsvLoader;
    private final MajorCsvLoader majorCsvLoader;
    private final CertificateCsvLoader certificateCsvLoader;
    private final CertificateLinkCsvLoader certificateLinkCsvLoader;
    private final CertificateImageCsvLoader certificateImageCsvLoader;
    private final CertificateCharacterCsvLoader certificateCharacterCsvLoader;

    @Override
    public void run(String... args) {
        // 삭제 순서 주의 (자식 → 부모)
        certificateCharacterCsvLoader.clear();
        certificateLinkCsvLoader.clear();
        certificateImageCsvLoader.clear();
        certificateCsvLoader.clear();
        majorCsvLoader.clear();
        departmentCsvLoader.clear();
        facultyCsvLoader.clear();

        // 삽입 순서: 부모 → 자식
        facultyCsvLoader.load();
        departmentCsvLoader.load();
        majorCsvLoader.load();
        certificateCsvLoader.load();
        certificateImageCsvLoader.load();
        certificateLinkCsvLoader.load();
        certificateCharacterCsvLoader.load();
    }
}
