package com.example.spring_certificate.Repository.CertificateRepository;

import com.example.spring_certificate.Entity.CertificateEntity.CertificateCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificateCharacterRepository extends JpaRepository<CertificateCharacter, Long> {
    List<CertificateCharacter> findByCertificate_Id(Long CertificateId);
}
