package com.example.spring_certificate.Service.assembler;

import com.example.spring_certificate.Dto.FacultyDto;
import com.example.spring_certificate.Entity.Department;
import com.example.spring_certificate.Entity.Faculty;
import com.example.spring_certificate.Service.Tree.DepartmentTreeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FacultyDtoAssembler {
    private final DepartmentTreeService departmentTreeService;

    public FacultyDto todto(Faculty faculty, List<Department> departments){
        FacultyDto dto = new FacultyDto();
        dto.setFacultyId(faculty.getId());
        dto.setFacultyName(faculty.getName());
        dto.setDepartments(departmentTreeService.buildDepartmentDtos(departments));
        return dto;
    }
}
