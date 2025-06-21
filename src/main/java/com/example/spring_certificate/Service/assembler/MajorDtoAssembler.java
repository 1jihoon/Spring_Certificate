package com.example.spring_certificate.Service.assembler;

import com.example.spring_certificate.Dto.CertificateDto.CertificateDto;
import com.example.spring_certificate.Dto.MajorDto;
import com.example.spring_certificate.Entity.CertificateEntity.Certificate;
import com.example.spring_certificate.Entity.Major;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MajorDtoAssembler {

    private final CertificateDtoAssembler certificateDtoAssembler;

    public MajorDto toDto(Major major, Map<Long, List<Certificate>> certsByMajorId) {
        MajorDto majorDto = new MajorDto();
        majorDto.setMajorId(major.getId());
        majorDto.setMajorName(major.getName());

        List<CertificateDto> certDtos = certsByMajorId.getOrDefault(major.getId(), List.of()).stream()
                .map(certificateDtoAssembler::toDto)
                .collect(Collectors.toList());

        majorDto.setCertificates(certDtos);
        return majorDto;
    }
}
