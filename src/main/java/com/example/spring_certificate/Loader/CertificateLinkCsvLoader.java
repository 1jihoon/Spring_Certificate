package com.example.spring_certificate.Loader;

import com.example.spring_certificate.Entity.Certificate;
import com.example.spring_certificate.Entity.CertificateLink;
import com.example.spring_certificate.Repository.CertificateLinkRepository;
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
public class CertificateLinkCsvLoader {

    private final CertificateRepository certificateRepository;
    private final CertificateLinkRepository certificateLinkRepository;

    public void clear() {
        certificateLinkRepository.deleteAll();
    }

    public void load() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new ClassPathResource("csv/certificate_link.csv").getInputStream(), StandardCharsets.UTF_8))) {

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

                CertificateLink link = new CertificateLink();
                link.setCertificate(cert);
                link.setUrl(tokens[1]);
                link.setDescription(tokens[2]);

                certificateLinkRepository.save(link);
            }

        } catch (IOException e) {
            throw new RuntimeException("Error loading certificate_link.csv", e);
        }
    }
}
