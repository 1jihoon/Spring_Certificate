package com.example.spring_certificate.Service.CommunityService;
import com.example.spring_certificate.Dto.CommunityDto.CommunityDto;
import com.example.spring_certificate.Dto.CommunityDto.CommunityImageDto;
import com.example.spring_certificate.Entity.CommunityEntity.Community;
import com.example.spring_certificate.Entity.CommunityEntity.CommunityImage;
import com.example.spring_certificate.Repository.CommunityRepository.CommunityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final CommunityFileService communityFileService;

    public CommunityDto toDto(Community post) {
        CommunityDto dto = new CommunityDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setWriter(post.getWriter());
        dto.setContent(post.getContent());
        dto.setDepartmentId(post.getDepartmentId());
        dto.setMajorId(post.getMajorId());
        dto.setCreatedAt(post.getCreatedAt()); // ✅ 추가
        dto.setViewCount(post.getViewCount());


        List<CommunityImageDto> imageDtos = post.getImages().stream().map(image -> {
            CommunityImageDto imgDto = new CommunityImageDto();
            imgDto.setId(image.getId());
            imgDto.setFileName(image.getFileName());
            return imgDto;
        }).collect(Collectors.toList());

        dto.setExistingImages(imageDtos);
        return dto;
    }

    @Transactional
    public void savePost(CommunityDto dto, List<MultipartFile> newImages,
                         String loginId, String type, String departmentId) {

        Community community = new Community();
        community.setTitle(dto.getTitle());
        community.setWriter(loginId);
        community.setContent(dto.getContent());

        if ("department".equals(type)) {
            community.setDepartmentId(departmentId);
            community.setMajorId(null);
        } else {
            community.setMajorId(departmentId);
            community.setDepartmentId(null);
        }

        // 새 이미지 처리
        if (newImages != null) {
            for (MultipartFile file : newImages) {
                if (!file.isEmpty()) {
                    String fileName = communityFileService.storeFile(file);
                    CommunityImage image = new CommunityImage();
                    image.setFileName(fileName);
                    community.addImage(image);
                }
            }
        }

        communityRepository.save(community);
    }


    @Transactional
    public void updatePost(Long id, CommunityDto dto,
                           List<MultipartFile> newImages,
                           List<Long> deleteImageIds) {

        Community post = communityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 게시글 없음"));

        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setDepartmentId(dto.getDepartmentId());
        post.setMajorId(dto.getMajorId());

        // 1. 기존 이미지 삭제 처리
        if (deleteImageIds != null) {
            List<CommunityImage> toRemove = post.getImages().stream()
                    .filter(img -> deleteImageIds.contains(img.getId()))
                    .collect(Collectors.toList());

            for (CommunityImage img : toRemove) {
                communityFileService.deleteFile(img.getFileName());
                post.removeImage(img);
            }
        }

        // 2. 새 이미지 저장
        if (newImages != null) {
            for (MultipartFile file : newImages) {
                if (!file.isEmpty()) {
                    String fileName = communityFileService.storeFile(file);
                    CommunityImage newImage = new CommunityImage();
                    newImage.setFileName(fileName);
                    post.addImage(newImage);
                }
            }
        }

        communityRepository.save(post);
    }

    @Transactional
    public void deletePost(Long id) {
        Community post = communityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        for (CommunityImage image : post.getImages()) {
            communityFileService.deleteFile(image.getFileName());
        }

        communityRepository.delete(post); // orphanRemoval=true 이므로 이미지도 자동 삭제
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
        Optional<Community> postOpt = communityRepository.findById(id);
        if (postOpt.isEmpty()) {
            System.out.println("❌ 게시글 ID 없음: " + id);
        }
        Community post = postOpt.orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        post.setViewCount(post.getViewCount() + 1);
        return communityRepository.save(post);
    }


}
