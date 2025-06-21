package com.example.spring_certificate.Loader.CertificateLoader;

import com.example.spring_certificate.Entity.CertificateEntity.Certificate;
import com.example.spring_certificate.Entity.CertificateEntity.CertificateLink;
import com.example.spring_certificate.Loader.DataLoader.DataCsvLoader;
import com.example.spring_certificate.Repository.CertificateRepository.CertificateLinkRepository;
import com.example.spring_certificate.Repository.CertificateRepository.CertificateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Component
@Order(6)
@RequiredArgsConstructor
public class CertificateLinkCsvLoader implements DataCsvLoader {

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
                line = line.trim();

                if(line.isBlank() || line.startsWith("#") || line.contains("certificateName")){
                    continue;
                }

                String[] tokens = line.split(",", -1);
                if (tokens.length < 3) {
                    continue;
                }

                String certificateName = tokens[0].trim(); // certificateId → name

                Optional<Certificate> certs = certificateRepository.findByName(certificateName);
                if (certs.isEmpty()) {
                    throw new RuntimeException("자격증 이름 '" + certificateName + "'을 찾을 수 없습니다.");
                }
                Certificate cert = certs.get(); // 또는 조건에 따라 적절한 것을 선택


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
