package com.example.spring_certificate.Controller;

import com.example.spring_certificate.Dto.CertificateDto;
import com.example.spring_certificate.Dto.FacultyDto;
import com.example.spring_certificate.Entity.Certificate;
import com.example.spring_certificate.Repository.CertificateRepository;
import com.example.spring_certificate.Service.CertificateService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class CertificateController {

    private final CertificateRepository certificateRepository;
    private final CertificateService certificateService;

    public CertificateController(CertificateService certificateService, CertificateRepository certificateRepository) {
        this.certificateService = certificateService;
        this.certificateRepository = certificateRepository;
    }

    @GetMapping("/certificates")
    public String showCertificateTree(Model model) {
        List<FacultyDto> faculties = certificateService.getFacultyTreeWithCertificates();
        model.addAttribute("faculties", faculties);
        return "certificate";
    }

    @GetMapping("/departments/{departmentId}/certificates")
    public String showCertificatesByDepartment(@PathVariable Long departmentId, Model model) {
        List<Certificate> certs = certificateService.getCertificatesByDepartmentId(departmentId);
        model.addAttribute("certs", certs);
        return "certificate";  // resources/templates/certificate.html
    }

    @GetMapping("/majors/{majorId}/certificates")
    public String showCertificatesByMajor(@PathVariable Long majorId, Model model) {
        List<Certificate> certs = certificateService.getCertificatesByMajorId(majorId);
        model.addAttribute("certs", certs);
        return "certificate";  // 재사용 가능
    }

    @GetMapping("/departments/{id}/certificates")
    public String getByDepartment(@PathVariable Long id, Model model) {
        List<Certificate> certs = certificateService.getCertificatesByDepartmentId(id);
        model.addAttribute("certificates", certs);
        return "certificate/list";
    }

    @GetMapping("/majors/{id}/certificates")
    public String getByMajor(@PathVariable Long id, Model model) {
        List<Certificate> certs = certificateService.getCertificatesByMajorId(id);
        model.addAttribute("certificates", certs);
        return "certificate/list";
    }

    @GetMapping("/certificates/{id}")
    public String getCertificateDetail(@PathVariable Long id, Model model) {
        Certificate cert = certificateRepository.findById(id).orElseThrow();
        CertificateDto dto = new CertificateDto();
        dto.setCertificateId(cert.getId());
        dto.setCertificateName(cert.getName());
        dto.setDetail(cert.getDetail());
        model.addAttribute("certificate", dto);
        return "detail";
    }


}
