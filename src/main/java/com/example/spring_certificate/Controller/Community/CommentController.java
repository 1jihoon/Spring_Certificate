package com.example.spring_certificate.Controller.Community;

import com.example.spring_certificate.Dto.CommunityDto.CommentDto;
import com.example.spring_certificate.Entity.CommunityEntity.Comment;
import com.example.spring_certificate.Service.CommunityService.CommentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{id}/comment")
    public String addComment(@PathVariable Long id,
                             @ModelAttribute CommentDto commentDto,
                             HttpSession session) {
        String loginId = (String) session.getAttribute("loginId");
        if (loginId == null) return "redirect:/login";

        commentService.saveComment(id, loginId, commentDto);

        return "redirect:/post/" + id;
        //requestMapping으로 한다고 해도 post까지 다 써야 정확함
    }

    @GetMapping("/comment/{id}/edit")
    public String editCommentForm(@PathVariable Long id, Model model, HttpSession session) {
        String loginId = (String) session.getAttribute("loginId");
        Comment comment = commentService.findById(id);

        if (!comment.getWriter().equals(loginId)) {
            return "redirect:/post/" + comment.getPost().getId(); // 권한 없음
        }

        model.addAttribute("comment", comment);
        return "comment/edit"; // 댓글 수정 페이지
    }

    @PostMapping("/comment/{id}/edit")
    public String editComment(@PathVariable Long id,
                              @RequestParam String content,
                              HttpSession session) {
        String loginId = (String) session.getAttribute("loginId");
        commentService.updateComment(id, content, loginId);
        Long postId = commentService.findById(id).getPost().getId();
        return "redirect:/post/" + postId;
    }

    @PostMapping("/comment/{id}/delete")
    public String deleteComment(@PathVariable Long id, HttpSession session) {
        String loginId = (String) session.getAttribute("loginId");
        Long postId = commentService.deleteComment(id, loginId);
        return "redirect:/post/" + postId;
    }
}
