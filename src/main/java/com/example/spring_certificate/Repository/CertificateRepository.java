package com.example.spring_certificate.Repository;

import com.example.spring_certificate.Entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    List<Certificate> findByDepartmentId(Long departmentId);
    List<Certificate> findByMajorId(Long majorId);
}
