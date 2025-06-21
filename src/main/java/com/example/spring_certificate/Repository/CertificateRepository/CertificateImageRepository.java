package com.example.spring_certificate.Repository.CertificateRepository;

import com.example.spring_certificate.Entity.CertificateEntity.CertificateImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificateImageRepository extends JpaRepository<CertificateImage, Long> {
    List<CertificateImage> findByCertificate_Id(Long certificateId);
}
