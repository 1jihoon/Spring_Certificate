package com.example.spring_certificate.Loader;

import com.example.spring_certificate.Entity.Certificate;
import com.example.spring_certificate.Entity.CertificateImage;
import com.example.spring_certificate.Repository.CertificateImageRepository;
import com.example.spring_certificate.Repository.CertificateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class CertificateImageCsvLoader {

    private final CertificateRepository certificateRepository;
    private final CertificateImageRepository certificateImageRepository;

    public void clear() {
        certificateImageRepository.deleteAll();
    }

    public void load() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new ClassPathResource("csv/certificate_image.csv").getInputStream(), StandardCharsets.UTF_8))) {

            reader.readLine(); // skip header
            String line;
            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) continue; // 빈 줄 무시

                String[] tokens = line.split(",", -1);
                if (tokens.length < 3) {
                    continue;
                }

                String certificateName = tokens[0].trim(); // certificateId → name

                Certificate cert = certificateRepository.findByName(certificateName)
                        .orElseThrow(() -> new RuntimeException("자격증 이름 '" + certificateName + "'을 찾을 수 없습니다."));


                CertificateImage image = new CertificateImage();
                image.setCertificate(cert);
                image.setUrl(tokens[1]);
                image.setAltText(tokens[2]);

                certificateImageRepository.save(image);
            }

        } catch (IOException e) {
            throw new RuntimeException("Error loading certificate_image.csv", e);
        }
    }
}
