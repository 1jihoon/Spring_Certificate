package com.example.spring_certificate.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DepartDto {
    private Long departmentId;
    private String departmentName;
    private List<MajorDto> majors;
    private List<CertificateDto> certificates;
}
