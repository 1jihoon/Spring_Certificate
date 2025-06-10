package com.example.spring_certificate.Repository;

import com.example.spring_certificate.Entity.CertificateCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificateCharacterRepository extends JpaRepository<CertificateCharacter, Long> {
    List<CertificateCharacter> findByCertificate_Id(Long CertificateId);
}
