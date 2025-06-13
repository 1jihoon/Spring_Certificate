package com.example.spring_certificate.Service.Tree;

import com.example.spring_certificate.Dto.FacultyDto;
import com.example.spring_certificate.Entity.Department;
import com.example.spring_certificate.Entity.Faculty;
import com.example.spring_certificate.Repository.DepartmentRepository;
import com.example.spring_certificate.Repository.FacultyRepository;
import com.example.spring_certificate.Service.assembler.FacultyDtoAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FacultyTreeService {

    private final FacultyRepository facultyRepository;
    private final DepartmentRepository departmentRepository;
    private final FacultyDtoAssembler facultyDtoAssembler;

    public List<FacultyDto> getFacultyTree() {
        List<Faculty> faculties = facultyRepository.findAll();
        List<Department> allDepts = departmentRepository.findAll();

        List<FacultyDto> result = new ArrayList<>();

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