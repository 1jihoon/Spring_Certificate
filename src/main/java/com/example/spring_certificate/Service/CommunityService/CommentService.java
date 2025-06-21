package com.example.spring_certificate.Service.CommunityService;

import com.example.spring_certificate.Dto.CommunityDto.CommentDto;
import com.example.spring_certificate.Entity.CommunityEntity.Comment;
import com.example.spring_certificate.Entity.CommunityEntity.Community;
import com.example.spring_certificate.Repository.CommunityRepository.CommentRepository;
import com.example.spring_certificate.Repository.CommunityRepository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommunityRepository communityRepository;

    public void saveComment(Long postId, String writer, CommentDto dto) {
        Community post = communityRepository.findById(postId).orElseThrow();

        Comment comment = Comment.builder()
                .content(dto.getContent())
                .writer(writer)
                .createdAt(LocalDateTime.now())
                .post(post)
                .build();

        commentRepository.save(comment);
    }

    public List<Comment> getComments(Long postId) {
        Community post = communityRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다. id=" + postId));
        return commentRepository.findByPostOrderByCreatedAtAsc(post);
    }

    // 댓글 단건 조회
    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
    }

    // 댓글 수정
    public void updateComment(Long id, String newContent, String loginId) {
        Comment comment = findById(id);
        if (!comment.getWriter().equals(loginId)) {
            throw new IllegalStateException("작성자만 수정할 수 있습니다.");
        }
        comment.setContent(newContent);
        commentRepository.save(comment);
    }

    // 댓글 삭제
    public Long deleteComment(Long id, String loginId) {
        Comment comment = findById(id);
        if (!comment.getWriter().equals(loginId)) {
            throw new IllegalStateException("작성자만 삭제할 수 있습니다.");
        }

        Long postId = comment.getPost().getId(); // 삭제 후 리디렉션용
        commentRepository.delete(comment);
        return postId;
    }
}
