package com.example.spring_certificate.Controller.Community;

import com.example.spring_certificate.Entity.CommunityEntity.Community;
import com.example.spring_certificate.Repository.DepartmentRepository;
import com.example.spring_certificate.Repository.MajorRepository;
import com.example.spring_certificate.Service.CommunityService.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class CommunityListController {
    private final CommunityService communityService;
    private final DepartmentRepository departmentRepository;
    private final MajorRepository majorRepository;

    private String findDepartmentNameById(String departmentId) {
        return departmentRepository.findNameById(Long.parseLong(departmentId))
                .orElse("알 수 없는 학과");
    }

    private String findMajorNameById(String majorId) {
        return majorRepository.findNameById(Long.parseLong(majorId))
                .orElse("알 수 없는 전공");
    }

    // 학과 게시글 목록
    @GetMapping("/department/{departmentId}")
    public String departmentPostList(@PathVariable String departmentId, Model model) {
        List<Community> posts = communityService.getPostsByDepartmentId(departmentId);
        model.addAttribute("posts", posts);
        model.addAttribute("departmentId", departmentId);
        model.addAttribute("departmentName", findDepartmentNameById(departmentId));
        model.addAttribute("type", "department");

        return "communitylist/communitylist";  // community_list.html
    }

    // 전공 게시글 목록
    @GetMapping("/major/{departmentId}")
    public String majorPostList(@PathVariable String departmentId, Model model) {
        // 전공 게시글 조회 시 majorId 컬럼을 기준으로 조회하도록 리포지토리 메서드가 필요합니다.
        List<Community> posts = communityService.getPostsByMajorId(departmentId);
        model.addAttribute("posts", posts);
        model.addAttribute("departmentId", departmentId);
        model.addAttribute("departmentName", findMajorNameById(departmentId));
        model.addAttribute("type", "major");

        return "communitylist/communitylist";  // community_list.html
    }
}
