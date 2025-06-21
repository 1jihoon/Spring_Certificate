package com.example.spring_certificate.Controller.Community;

import com.example.spring_certificate.Dto.CommunityDto.CommentDto;
import com.example.spring_certificate.Dto.CommunityDto.CommunityDto;
import com.example.spring_certificate.Entity.CommunityEntity.Comment;
import com.example.spring_certificate.Entity.CommunityEntity.Community;
import com.example.spring_certificate.Repository.DepartmentRepository;
import com.example.spring_certificate.Repository.MajorRepository;
import com.example.spring_certificate.Service.CommunityService.CommentService;
import com.example.spring_certificate.Service.CommunityService.CommunityService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class CommunityDetailController {
    private final CommunityService communityService;
    private final CommentService commentService;
    private final DepartmentRepository departmentRepository;
    private final MajorRepository majorRepository;

    // 게시글 상세 페이지
    @GetMapping("/{id}")
    public String getPostDetail(@PathVariable Long id, Model model, HttpSession session) {
        Community post = communityService.findByIdAndIncrementView(id);
        CommunityDto dto = communityService.toDto(post);
        model.addAttribute("post", dto);

        List<Comment> comments = commentService.getComments(id);
        model.addAttribute("comments", comments);
        model.addAttribute("commentDto", new CommentDto());

        // ✅ 로그인한 사용자 id 전달
        model.addAttribute("loginId", session.getAttribute("loginId"));

        String type;
        String backUrl;

        if (dto.getDepartmentId() != null && !dto.getDepartmentId().isBlank()) {
            type = "department";
            backUrl = "/posts/department/" + dto.getDepartmentId();
            departmentRepository.findNameById(Long.parseLong(dto.getDepartmentId()))
                    .ifPresent(name -> model.addAttribute("departmentName", name));
        } else {
            type = "major";
            backUrl = "/posts/major/" + dto.getMajorId();
            majorRepository.findNameById(Long.parseLong(dto.getMajorId()))
                    .ifPresent(name -> model.addAttribute("majorName", name));
        }

        model.addAttribute("type", type);
        model.addAttribute("backUrl", backUrl);

        return "communitydetail/communitydetail";
    }


    // 게시글 삭제
    @PostMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id) {
        Community post = communityService.findByIdAndIncrementView(id); // 삭제 전 조회

        communityService.deletePost(id);

        String redirectUrl;
        if (post.getDepartmentId() != null && !post.getDepartmentId().isBlank()) {
            redirectUrl = "/posts/department/" + post.getDepartmentId();
        } else if (post.getMajorId() != null && !post.getMajorId().isBlank()) {
            redirectUrl = "/posts/major/" + post.getMajorId();
        } else {
            redirectUrl = "/"; // fallback
        }

        return "redirect:" + redirectUrl;
    }

    // 게시글 수정 폼 보여주기
    @GetMapping("/edit/{id}")
    public String editPostForm(@PathVariable Long id, Model model) {
        Community post = communityService.findByIdAndIncrementView(id);
        CommunityDto dto = communityService.toDto(post);

        model.addAttribute("communityDto", dto);
        return "write/writelayout";
    }

    // 게시글 수정 처리
    @PostMapping("/edit/{id}")
    public String updatePost(@PathVariable Long id,
                             @ModelAttribute CommunityDto dto,
                             @RequestParam(value = "newImages", required = false) List<MultipartFile> newImages,
                             @RequestParam(value = "deleteImageIds", required = false) List<Long> deleteImageIds) {

        communityService.updatePost(id, dto, newImages, deleteImageIds);
        return "redirect:/post/" + id;
    }

}
