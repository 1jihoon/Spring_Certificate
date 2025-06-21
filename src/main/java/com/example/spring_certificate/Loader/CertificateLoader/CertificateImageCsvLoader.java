package com.example.spring_certificate.Loader.CertificateLoader;

import com.example.spring_certificate.Entity.CertificateEntity.Certificate;
import com.example.spring_certificate.Entity.CertificateEntity.CertificateImage;
import com.example.spring_certificate.Loader.DataLoader.DataCsvLoader;
import com.example.spring_certificate.Repository.CertificateRepository.CertificateImageRepository;
import com.example.spring_certificate.Repository.CertificateRepository.CertificateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Component
@Order(5)
@RequiredArgsConstructor
public class CertificateImageCsvLoader implements DataCsvLoader {

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
                line = line.trim();

                if (line.isBlank() || line.startsWith("#") || line.contains("certificateName")) {
                    continue;
                }

                String[] tokens = line.split(",", -1);
                if (tokens.length < 3) continue;

                String certificateName = tokens[0].trim();
                String imageUrl = tokens[1].trim();
                String altText = tokens[2].trim();

                Optional<Certificate> certs = certificateRepository.findByName(certificateName);
                if (certs.isEmpty()) {
                    System.out.println("❌ 매칭 실패: " + certificateName);
                    continue;
                }

                Certificate cert = certs.get();

                CertificateImage image = new CertificateImage();
                image.setCertificate(cert);
                image.setUrl(imageUrl);
                image.setAltText(altText);

                certificateImageRepository.save(image);
                System.out.println("✅ 저장됨: " + certificateName + " → " + imageUrl);
            }

        } catch (IOException e) {
            throw new RuntimeException("Error loading certificate_image.csv", e);
        }
    }
}
