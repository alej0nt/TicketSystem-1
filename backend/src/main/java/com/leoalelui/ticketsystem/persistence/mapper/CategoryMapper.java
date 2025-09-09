package com.leoalelui.ticketsystem.persistence.mapper;

import com.leoalelui.ticketsystem.domain.dto.*;
import com.leoalelui.ticketsystem.persistence.entity.Category;
import com.leoalelui.ticketsystem.persistence.entity.EmployeeEntity;
import org.mapstruct.*;

@Mapper (componentModel = "spring")
public interface CategoryMapper {

    CategoryCreateDTO toDTO(Category category);
    Category toEntity(CategoryCreateDTO categoryCreateDTO);
    CategoryResponseDTO toResponseDTO(Category category);
}
