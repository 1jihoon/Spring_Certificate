package com.example.spring_certificate.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FacultyDto {
    private Long facultyId;
    private String facultyName;
    private List<DepartDto> departments;
}
