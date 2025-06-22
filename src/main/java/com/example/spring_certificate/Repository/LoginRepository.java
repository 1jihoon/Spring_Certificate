package com.example.spring_certificate.Repository;

import com.example.spring_certificate.Entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, Long> {
    Optional<Login> findById(String id);
    boolean existsById(String id);
    boolean existsByEmail(String email);
}
