package com.example.spring_certificate.Service.assembler;

import com.example.spring_certificate.Dto.CertificateDto;
import com.example.spring_certificate.Dto.DepartDto;
import com.example.spring_certificate.Dto.MajorDto;
import com.example.spring_certificate.Entity.Certificate;
import com.example.spring_certificate.Entity.Department;
import com.example.spring_certificate.Entity.Major;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DepartmentDtoAssembler {
    private final MajorDtoAssembler majorDtoAssembler;
    private final CertificateDtoAssembler certificateDtoAssembler;

    public DepartDto toDto(Department dept,
                                     Map<Long, List<Major>> majorsByDeptId,
                                     Map<Long, List<Certificate>> certsByDeptId,
                                     Map<Long, List<Certificate>> certsByMajorId) {

        DepartDto departDto = new DepartDto();
        departDto.setDepartmentId(dept.getId());
        departDto.setDepartmentName(dept.getName());

        List<Major> majors = majorsByDeptId.getOrDefault(dept.getId(), List.of());
        List<MajorDto> majorDtos = majors.stream()
                .map(major -> majorDtoAssembler.toDto(major, certsByMajorId))
                .toList();

        List<CertificateDto> certDtos = certsByDeptId.getOrDefault(dept.getId(), List.of()).stream()
                .map(certificateDtoAssembler::toDto)
                .toList();

        departDto.setMajors(majorDtos);
        departDto.setCertificates(certDtos);
        return departDto;
    }
}
