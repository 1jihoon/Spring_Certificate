package com.example.spring_certificate.Service.Tree;


import com.example.spring_certificate.Dto.DepartDto;
import com.example.spring_certificate.Dto.FacultyDto;
import com.example.spring_certificate.Entity.Certificate;
import com.example.spring_certificate.Entity.Department;
import com.example.spring_certificate.Entity.Faculty;
import com.example.spring_certificate.Repository.CertificateRepository;
import com.example.spring_certificate.Repository.DepartmentRepository;
import com.example.spring_certificate.Repository.FacultyRepository;
import com.example.spring_certificate.Service.assembler.DepartmentDtoAssembler;
import com.example.spring_certificate.Service.assembler.FacultyDtoAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FacultyTreeService {

    private final FacultyRepository facultyRepository;
    private final DepartmentRepository departmentRepository;
    private final FacultyDtoAssembler facultyDtoAssembler;
    private final DepartmentDtoAssembler departmentDtoAssembler;
    private final CertificateRepository certificateRepository;

    public List<FacultyDto> getFacultyTree() {
        List<Faculty> faculties = facultyRepository.findAll();
        List<Department> allDepts = departmentRepository.findAll();

        List<FacultyDto> result = new ArrayList<>();

        /*List<Department> orphanDepts = allDepts.stream()
                        .filter(d -> d.getFaculty() == null).toList();

        if(!orphanDepts.isEmpty()) {
            FacultyDto pseudo = new FacultyDto();
            pseudo.setFacultyId(-1L);
            pseudo.setFacultyName("자유전공학과");

            Map<Long, List<Certificate>> orphanCertsByDeptId = allCertificates.stream()
                            .filter(cert -> cert.getDepartment() != null && cert.getMajor() == null)
                            .collect(Collectors.groupingBy(cert -> cert.getDepartment().getId()));

            List<DepartDto> orphanDeptDtos = orphanDepts.stream()
                            .map(dept -> departmentDtoAssembler.toDto(
                                    dept,
                                    Map.of(),
                                    orphanCertsByDeptId,
                                    Map.of()
                            ))
                            .toList();
            pseudo.setDepartments(orphanDeptDtos); // ✅ 이거 반드시 추가해야 학과 출력됨
            result.add(pseudo);
        }*/

        result.addAll(faculties.stream()
                .map(faculty -> {
                    List<Department> deptList = allDepts.stream()
                            .filter(d -> d.getFaculty() != null && d.getFaculty().getId().equals(faculty.getId()))
                            .toList();

                    return facultyDtoAssembler.todto(faculty, deptList);
                }).toList());

        return result;
    }
}
