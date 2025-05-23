package com.example.spring_certificate.Repository;

import com.example.spring_certificate.Entity.Department;
import com.example.spring_certificate.Entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {}
