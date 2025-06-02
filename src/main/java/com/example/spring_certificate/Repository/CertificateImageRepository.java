package com.example.spring_certificate.Repository;

import com.example.spring_certificate.Entity.CertificateImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificateImageRepository extends JpaRepository<CertificateImage, Long> {
    List<CertificateImage> findByCertificate_Id(Long certificateId);
}
