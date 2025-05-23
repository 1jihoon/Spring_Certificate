package com.example.spring_certificate.Repository;

import com.example.spring_certificate.Entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepository extends JpaRepository<Faculty, Long>{}
