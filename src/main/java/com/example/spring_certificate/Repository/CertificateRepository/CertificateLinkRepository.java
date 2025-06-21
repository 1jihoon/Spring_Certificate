package com.example.spring_certificate.Repository.CertificateRepository;

import com.example.spring_certificate.Entity.CertificateEntity.CertificateLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificateLinkRepository extends JpaRepository<CertificateLink, Long> {
    List<CertificateLink> findByCertificate_Id(Long certificateId);
}
