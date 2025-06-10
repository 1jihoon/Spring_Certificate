package com.example.spring_certificate.Dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CertificateCharacterDto {
    private Long CertificateCharacterId;
    private String url;
    private String altText;

    public CertificateCharacterDto(String url, String altText){
        this.url = url;
        this.altText = altText;
    }
}
