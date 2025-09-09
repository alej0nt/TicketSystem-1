package com.leoalelui.ticketsystem.persistence.mapper;

import com.leoalelui.ticketsystem.domain.dto.request.CategoryCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.CategoryResponseDTO;
import com.leoalelui.ticketsystem.persistence.entity.CategoryEntity;
import org.mapstruct.*;

@Mapper (componentModel = "spring")
public interface CategoryMapper {

    CategoryCreateDTO toDTO(CategoryEntity category);
    CategoryEntity toEntity(CategoryCreateDTO categoryCreateDTO);
    CategoryResponseDTO toResponseDTO(CategoryEntity category);
}
