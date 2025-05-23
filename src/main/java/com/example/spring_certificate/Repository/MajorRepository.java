package com.example.spring_certificate.Repository;

import com.example.spring_certificate.Entity.Faculty;
import com.example.spring_certificate.Entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MajorRepository extends JpaRepository<Major, Long> {}
