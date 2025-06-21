package com.example.spring_certificate.Controller.Community;

import com.example.spring_certificate.Dto.CommunityDto.CommunityDto;
import com.example.spring_certificate.Service.CommunityService.CommunityService;
import com.example.spring_certificate.Service.Tree.FacultyTreeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/write")
public class CommunityWriteController {
    private final FacultyTreeService facultyTreeService;
    private final CommunityService communityService;

    @GetMapping
    public String writeForm(Model model, HttpSession session) {
        if (session.getAttribute("loginId") == null) return "redirect:/login";
        model.addAttribute("faculties", facultyTreeService.getFacultyTree());
        return "select/selectlayout";
    }

    // 글쓰기 폼 (학과 / 전공 구분)
    @GetMapping({"/department/{departmentId}", "/major/{departmentId}"})
    public String writeFormByDeptOrMajor(@PathVariable String departmentId,
                                         HttpServletRequest request,
                                         Model model, HttpSession session) {
        if (session.getAttribute("loginId") == null) {
            return "redirect:/login";
        }
        model.addAttribute("departmentId", departmentId);
        model.addAttribute("communityDto", new CommunityDto());

        // URL에서 'department' 혹은 'major' 추출
        String uri = request.getRequestURI();  // 예: /write/department/22
        String[] parts = uri.split("/");
        String type = parts.length > 2 ? parts[2] : "department"; // 기본값 department
        model.addAttribute("type", type);

        return "write/writelayout";  // write_community.html
    }

    // 글쓰기 처리 (학과 / 전공 공통)
    @PostMapping({"/department/{departmentId}", "/major/{departmentId}"})
    public String submitPost(@PathVariable String departmentId,
                             HttpServletRequest request,
                             @RequestParam(value = "newImages", required = false) List<MultipartFile> newImages,
                             @ModelAttribute CommunityDto communityDto,
                             HttpSession session) {
        String loginId = (String) session.getAttribute("loginId");
        if (loginId == null) {
            return "redirect:/login";
        }

        String type = communityService.extractTypeFromUri(request.getRequestURI());

        // ✅ 다중 이미지 구조로 저장
        communityService.savePost(communityDto, newImages, loginId, type, departmentId);


        return "redirect:/posts/" + type + "/" + departmentId;
    }
}
