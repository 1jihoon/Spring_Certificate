package com.example.spring_certificate.Repository.CommunityRepository;

import com.example.spring_certificate.Entity.CommunityEntity.Comment;
import com.example.spring_certificate.Entity.CommunityEntity.Community;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostOrderByCreatedAtAsc(Community post);
}
