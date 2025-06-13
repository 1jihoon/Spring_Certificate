package com.example.spring_certificate.Controller;

import com.example.spring_certificate.Dto.CertificateDto;
import com.example.spring_certificate.Entity.Certificate;
import com.example.spring_certificate.Repository.CertificateRepository;
import com.example.spring_certificate.Service.assembler.CertificateDtoAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor//이거 쓰면 생성자를 안 만들어도 된다.
public class CertificateDetailController {

    private final CertificateRepository certificateRepository;
    private final CertificateDtoAssembler certificateDtoAssembler;

    @GetMapping("/certificates/{id}")
    public String getCertificateDetail(@PathVariable Long id, Model model) {
       Certificate cert = certificateRepository.findById(id).orElseThrow();
       CertificateDto dto = certificateDtoAssembler.toDto(cert);
       model.addAttribute("certificate",dto);//certificate란 이름으로 dto가 전달됨
       return "detail";
    }
}