package com.example.spring_certificate.Dto.CertificateDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CertificateLinkDto {
    private Long certificateLinkId;
    private String url;
    private String description;

    public CertificateLinkDto(String url, String description) {
        this.url = url;
        this.description = description;
    }
}
