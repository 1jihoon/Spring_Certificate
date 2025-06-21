package com.example.spring_certificate.Repository.CommunityRepository;

import com.example.spring_certificate.Entity.CommunityEntity.Community;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    List<Community> findByDepartmentId(String departmentId);
    List<Community> findByMajorId(String majorId);
}
