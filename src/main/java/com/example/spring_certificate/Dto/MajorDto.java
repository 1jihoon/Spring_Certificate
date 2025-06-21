package com.example.spring_certificate.Dto;

import com.example.spring_certificate.Dto.CertificateDto.CertificateDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MajorDto {
    private Long majorId;
    private String majorName;
    private Long mapId;
    private List<CertificateDto> certificates = new ArrayList<>();
}
