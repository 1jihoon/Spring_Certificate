package com.example.spring_certificate.Service;


import com.example.spring_certificate.Dto.CommunityDto;
import com.example.spring_certificate.Entity.Community;
import com.example.spring_certificate.Repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;

    public void savePost(CommunityDto dto, String loginId, String type, String departmentId){
        Community community = new Community();
        community.setTitle(dto.getTitle());
        community.setWriter(loginId); // ✅ loginId를 직접 사용해야 함
        community.setContent(dto.getContent());

        if("department".equals(type)){
            community.setDepartmentId(departmentId);
            community.setMajorId(null);
        }
        else{
            community.setMajorId(departmentId);
            community.setDepartmentId(null);
        }

        communityRepository.save(community);
    }

    public String extractTypeFromUri(String uri) {
        String[] parts = uri.split("/");
        return parts.length > 2 ? parts[2] : "department";
    }

    public List<Community> getPostsByDepartmentId(String departmentId){
        return communityRepository.findByDepartmentId(departmentId);
    }

    public List<Community> getPostsByMajorId(String majorId){
        return communityRepository.findByMajorId(majorId);
    }

    public Community findByIdAndIncrementView(Long id) {
        Community post = communityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        post.setViewCount(post.getViewCount() + 1);
        return communityRepository.save(post);
    }

}
