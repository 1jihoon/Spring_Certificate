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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentTreeService {

    private final MajorRepository majorRepository;
    private final CertificateRepository certificateRepository;
    private final DepartmentDtoAssembler departmentDtoAssembler;

    public List<DepartDto> buildDepartmentDtos(List<Department> departments) {
        List<Major> majors = majorRepository.findByDepartmentIds(
                departments.stream().map(Department::getId).toList());

        List<Certificate> deptCertificates = certificateRepository.findByDepartmentIds(
                departments.stream().map(Department::getId).toList());

        List<Certificate> majorCertificates = certificateRepository.findByMajorIds(
                majors.stream().map(Major::getId).toList());

        Map<Long, List<Major>> majorsByDeptId = majors.stream()
                .collect(Collectors.groupingBy(m -> m.getDepartment().getId()));

        Map<Long, List<Certificate>> certsByDeptId = deptCertificates.stream()
                .collect(Collectors.groupingBy(c -> c.getDepartment().getId()));

        Map<Long, List<Certificate>> certsByMajorId = majorCertificates.stream()
                .collect(Collectors.groupingBy(c -> c.getMajor().getId()));

        return departments.stream()
                .map(dept -> departmentDtoAssembler.toDto(dept, majorsByDeptId, certsByDeptId, certsByMajorId))
                .toList();
    }
}

