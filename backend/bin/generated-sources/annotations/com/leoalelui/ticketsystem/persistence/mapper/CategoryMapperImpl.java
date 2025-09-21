package com.leoalelui.ticketsystem.persistence.mapper;

import com.leoalelui.ticketsystem.domain.dto.request.CategoryCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.CategoryResponseDTO;
import com.leoalelui.ticketsystem.persistence.entity.CategoryEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-21T16:15:10-0500",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryCreateDTO toDTO(CategoryEntity category) {
        if ( category == null ) {
            return null;
        }

        CategoryCreateDTO categoryCreateDTO = new CategoryCreateDTO();

        categoryCreateDTO.setDescription( category.getDescription() );
        categoryCreateDTO.setName( category.getName() );

        return categoryCreateDTO;
    }

    @Override
    public CategoryEntity toEntity(CategoryCreateDTO categoryCreateDTO) {
        if ( categoryCreateDTO == null ) {
            return null;
        }

        CategoryEntity categoryEntity = new CategoryEntity();

        categoryEntity.setDescription( categoryCreateDTO.getDescription() );
        categoryEntity.setName( categoryCreateDTO.getName() );

        return categoryEntity;
    }

    @Override
    public CategoryResponseDTO toResponseDTO(CategoryEntity category) {
        if ( category == null ) {
            return null;
        }

        String description = null;
        Long id = null;
        String name = null;

        description = category.getDescription();
        id = category.getId();
        name = category.getName();

        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO( id, name, description );

        return categoryResponseDTO;
    }
}
