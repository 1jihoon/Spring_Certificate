package com.example.spring_certificate.Controller;

import com.example.spring_certificate.Dto.CommunityDto;
import com.example.spring_certificate.Entity.Community;
import com.example.spring_certificate.Entity.Major;
import com.example.spring_certificate.Repository.CommunityRepository;
import com.example.spring_certificate.Repository.DepartmentRepository;
import com.example.spring_certificate.Repository.MajorRepository;
import com.example.spring_certificate.Service.CommunityService;
import com.example.spring_certificate.Service.Tree.FacultyTreeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommunityController {

    private final FacultyTreeService facultyTreeService;
    private final DepartmentRepository departmentRepository;
    private final CommunityService communityService;
    private final MajorRepository majorRepository;

    private String findDepartmentNameById(String departmentId) {
        return departmentRepository.findNameById(Long.parseLong(departmentId))
                .orElse("알 수 없는 학과");
    }

    private String findMajorNameById(String majorId) {
        return majorRepository.findNameById(Long.parseLong(majorId))
                .orElse("알 수 없는 전공");
    }

    // 학부 / 전공 선택 화면 (글쓰기 시작)
    @GetMapping("/write")
    public String writeForm(Model model, HttpSession session) {
        if (session.getAttribute("loginId") == null) {
            return "redirect:/login";
        }
        model.addAttribute("faculties", facultyTreeService.getFacultyTree());
        return "selectdepartment";  // selectdepartment.html
    }

    // 글쓰기 폼 (학과 / 전공 구분)
    @GetMapping({"/write/department/{departmentId}", "/write/major/{departmentId}"})
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

        return "write_community";  // write_community.html
    }

    // 글쓰기 처리 (학과 / 전공 공통)
    @PostMapping({"/write/department/{departmentId}", "/write/major/{departmentId}"})
    public String submitPost(@PathVariable String departmentId,
                             HttpServletRequest request,
                             @ModelAttribute CommunityDto communityDto,
                             HttpSession session) {
        String loginId = (String) session.getAttribute("loginId");
        if (loginId == null) {
            return "redirect:/login";
        }

        String type = communityService.extractTypeFromUri(request.getRequestURI());

        communityService.savePost(communityDto, loginId, type, departmentId);

        return "redirect:/posts/" + type + "/" + departmentId;
    }

    // 학과 게시글 목록
    @GetMapping("/posts/department/{departmentId}")
    public String departmentPostList(@PathVariable String departmentId, Model model) {
        List<Community> posts = communityService.getPostsByDepartmentId(departmentId);
        model.addAttribute("posts", posts);
        model.addAttribute("departmentId", departmentId);
        model.addAttribute("departmentName", findDepartmentNameById(departmentId));
        model.addAttribute("type", "department");

        return "community_list";  // community_list.html
    }

    // 전공 게시글 목록
    @GetMapping("/posts/major/{departmentId}")
    public String majorPostList(@PathVariable String departmentId, Model model) {
        // 전공 게시글 조회 시 majorId 컬럼을 기준으로 조회하도록 리포지토리 메서드가 필요합니다.
        List<Community> posts = communityService.getPostsByMajorId(departmentId);
        model.addAttribute("posts", posts);
        model.addAttribute("departmentId", departmentId);
        model.addAttribute("departmentName", findMajorNameById(departmentId));
        model.addAttribute("type", "major");

        return "community_list";  // community_list.html
    }

    // 게시글 상세 페이지
    @GetMapping("/post/{id}")
    public String getPostDetail(@PathVariable Long id, Model model) {
        Community post = communityService.findByIdAndIncrementView(id);
        model.addAttribute("post", post);

        String type;
        String backUrl;
        if (post.getDepartmentId() != null) {
            type = "department";
            backUrl = "/posts/department/" + post.getDepartmentId();

            // 이름 조회
            departmentRepository.findNameById(Long.parseLong(post.getDepartmentId()))
                    .ifPresent(name -> model.addAttribute("departmentName", name));
        } else {
            type = "major";
            backUrl = "/posts/major/" + post.getMajorId();

            majorRepository.findNameById(Long.parseLong(post.getMajorId()))
                    .ifPresent(name -> model.addAttribute("majorName", name));

            /*// major에 속한 department 이름도 추가로 불러올 수 있다면 이때도 추가
            Major major = majorRepository.findById(Long.parseLong(post.getMajorId())).orElse(null);
            if (major != null && major.getDepartment() != null) {
                model.addAttribute("departmentName", major.getDepartment().getName());
            }*/
        }

        model.addAttribute("type", type);
        model.addAttribute("backUrl", backUrl);  // ✅ 추가

        return "community_detail";  // community_detail.html
    }
}
