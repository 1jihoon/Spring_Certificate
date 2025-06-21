package com.example.spring_certificate.Dto.CommunityDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommunityImageDto {
    private Long id;
    private String fileName;

    // 필요 시 순서, 설명 등도 추가 가능
    // private int sortOrder;
    // private String caption;
}
