package com.example.spring_certificate.Controller;

import com.example.spring_certificate.Dto.FacultyDto;
import com.example.spring_certificate.Service.Tree.FacultyTreeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CertificateTreeController {
    private final FacultyTreeService treeService;

    @GetMapping("/certificates")
    public String showCertificateTree(Model model) {
        List<FacultyDto> faculties = treeService.getFacultyTree();
        model.addAttribute("faculties", faculties);
        return "certificate";
    }
}

