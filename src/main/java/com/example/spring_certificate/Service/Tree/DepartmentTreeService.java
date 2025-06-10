package com.example.spring_certificate.Service.Tree;

import com.example.spring_certificate.Dto.DepartDto;
import com.example.spring_certificate.Entity.Certificate;
import com.example.spring_certificate.Entity.Department;
import com.example.spring_certificate.Entity.Major;
import com.example.spring_certificate.Repository.CertificateRepository;
import com.example.spring_certificate.Repository.MajorRepository;
import com.example.spring_certificate.Service.assembler.DepartmentDtoAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentTreeService {

    private final MajorRepository majorRepository;
    private final CertificateRepository certificateRepository;
    private final DepartmentDtoAssembler departmentDtoAssembler;

    public List<DepartDto> buildDepartmentDtos(List<Department> departments) {
        // ✅ 1. 전공 전체 조회
        List<Major> majors = majorRepository.findByDepartmentIds(
                departments.stream().map(Department::getId).toList());

        // ✅ 2. 부서별 자격증 조회
        List<Certificate> deptCertificates = certificateRepository.findByDepartmentIds(
                departments.stream().map(Department::getId).toList());

        // ✅ 3. 전공별로 직접 연결된 자격증 추출 (단방향)
        Map<Long, List<Certificate>> certsByMajorId = majors.stream()
                .collect(Collectors.toMap(
                        Major::getId,
                        major -> new ArrayList<>(major.getCertificates())  // 단방향 접근
                ));

        // ✅ 4. 부서 → 전공 매핑
        Map<Long, List<Major>> majorsByDeptId = majors.stream()
                .collect(Collectors.groupingBy(m -> m.getDepartment().getId()));

        // ✅ 5. 부서 → 자격증 매핑
        Map<Long, List<Certificate>> certsByDeptId = deptCertificates.stream()
                .collect(Collectors.groupingBy(c -> c.getDepartment().getId()));

        // ✅ 6. 디버그 로그
        System.out.println("📌 전공별 자격증 수: " + certsByMajorId.size());

        // ✅ 7. 최종 DTO 생성
        return departments.stream()
                .map(dept -> departmentDtoAssembler.toDto(
                        dept,
                        majorsByDeptId,
                        certsByDeptId,
                        certsByMajorId
                ))
                .toList();
    }
}
