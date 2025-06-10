package com.example.spring_certificate.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CertificateDto {
    private Long certificateId;
    private String certificateName;
    private String detail;
    private List<CertificateImageDto> images;
    private List<CertificateLinkDto> links;
    private List<CertificateCharacterDto> characters;
}
