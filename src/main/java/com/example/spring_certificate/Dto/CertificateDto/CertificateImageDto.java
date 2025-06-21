package com.example.spring_certificate.Dto.CertificateDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CertificateImageDto {
    private Long certificateImageId;
    private String url;
    private String altText;

    // 필수! 생성자
    public CertificateImageDto(String url, String altText) {
        this.url = url;
        this.altText = altText;
    }

}
