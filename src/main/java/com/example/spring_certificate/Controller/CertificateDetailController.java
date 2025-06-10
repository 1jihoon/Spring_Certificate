package com.example.spring_certificate.Controller;

import com.example.spring_certificate.Dto.CertificateCharacterDto;
import com.example.spring_certificate.Dto.CertificateDto;
import com.example.spring_certificate.Dto.CertificateImageDto;
import com.example.spring_certificate.Dto.CertificateLinkDto;
import com.example.spring_certificate.Entity.Certificate;
import com.example.spring_certificate.Entity.CertificateCharacter;
import com.example.spring_certificate.Entity.CertificateImage;
import com.example.spring_certificate.Entity.CertificateLink;
import com.example.spring_certificate.Repository.CertificateCharacterRepository;
import com.example.spring_certificate.Repository.CertificateImageRepository;
import com.example.spring_certificate.Repository.CertificateLinkRepository;
import com.example.spring_certificate.Repository.CertificateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor//이거 쓰면 생성자를 안 만들어도 된다.
public class CertificateDetailController {

    private final CertificateRepository certificateRepository;
    private final CertificateImageRepository certificateImageRepository;
    private final CertificateLinkRepository certificateLinkRepository;
    private final CertificateCharacterRepository certificateCharacterRepository;

    @GetMapping("/certificates/{id}")
    public String getCertificateDetail(@PathVariable Long id, Model model) {
        //GetMapping에 있는 Url에 있는 id를 Long 형태로 바꿔주기 위해서 PathVarible을 쓴다.
        Certificate cert = certificateRepository.findById(id).orElseThrow();

        List<CertificateImage> images = certificateImageRepository.findByCertificate_Id(cert.getId());
        List<CertificateLink> links = certificateLinkRepository.findByCertificate_Id(cert.getId());
        List<CertificateCharacter> characters = certificateCharacterRepository.findByCertificate_Id(cert.getId());

        List<CertificateImageDto> imageDtos = images.stream()
                .map(img -> new CertificateImageDto(img.getUrl(), img.getAltText()))
                .toList();

        List<CertificateLinkDto> linkDtos = links.stream()
                .map(link -> new CertificateLinkDto(link.getUrl(), link.getDescription()))
                .toList();

        List<CertificateCharacterDto> characterDtos = characters.stream()
                .map(character -> new CertificateCharacterDto(character.getUrl(), character.getAltText()))
                .toList();

        CertificateDto dto = new CertificateDto();
        dto.setCertificateId(cert.getId());
        dto.setCertificateName(cert.getName());
        dto.setDetail(cert.getDetail());
        dto.setImages(imageDtos);
        dto.setLinks(linkDtos);
        dto.setCharacters(characterDtos);
        model.addAttribute("certificate", dto);
        //view로 데이터를 전달할 떄 사용하는 객체로 model.addAttribute를 이용해 뷰 템플릿에서
        //키로 사용가능하다.
        return "detail";
    }
}