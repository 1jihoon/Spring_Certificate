package com.example.spring_certificate.Service.assembler;

import com.example.spring_certificate.Dto.CommunityDto;
import com.example.spring_certificate.Entity.Community;
import org.springframework.stereotype.Component;

@Component
public class CommunityDtoAssembler {

    public static Community toEntity(CommunityDto dto) {
        Community entity = new Community();
        entity.setTitle(dto.getTitle());
        entity.setWriter(dto.getWriter());
        entity.setContent(dto.getContent());
        return entity;
    }

    public static CommunityDto toDto(Community entity) {
        CommunityDto dto = new CommunityDto();
        dto.setTitle(entity.getTitle());
        dto.setWriter(entity.getWriter());
        dto.setContent(entity.getContent());
        return dto;
    }
}

