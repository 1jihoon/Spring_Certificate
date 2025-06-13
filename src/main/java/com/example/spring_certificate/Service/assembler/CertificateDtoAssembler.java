package com.example.spring_certificate.Service.assembler;

import com.example.spring_certificate.Dto.*;
import com.example.spring_certificate.Entity.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CertificateDtoAssembler {
    public CertificateDto toDto(Certificate cert) {
        CertificateDto dto = new CertificateDto();
        dto.setCertificateId(cert.getId());
        dto.setCertificateName(cert.getName());
        dto.setDetail(cert.getDetail());

        // ImageDto 매핑
        List<CertificateImageDto> imageDtos = cert.getImages().stream()
                .map(img -> new CertificateImageDto(img.getUrl(), img.getAltText()))
                .toList();

        // LinkDto 매핑
        List<CertificateLinkDto> linkDtos = cert.getLinks().stream()
                .map(link -> new CertificateLinkDto(link.getUrl(),link.getDescription()))
                .toList();

        //CharacterDto 매핑
        List<CertificateCharacterDto> characterDtos = cert.getCharacters().stream()
                        .map(Character -> new CertificateCharacterDto(Character.getUrl(), Character.getAltText()))
                        .toList();

        dto.setImages(imageDtos);
        dto.setLinks(linkDtos);
        dto.setCharacters(characterDtos);
        return dto;
    }
    //toFacultyDto,toDepartmentDto,toMajorDto,toCertificateDto 메소드는 각각 학부 - 학과 - 전공 - 자격증을 넣는 구조를
    //띈 상태에서 기본키를 상징하는 Id를 중심으로 정보를 학부(학과(전공(자겨증) OR 자격증)) 이런 식으로 돼있는 구조를
    //볼 수 있다.
}