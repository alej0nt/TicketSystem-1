package com.leoalelui.ticketsystem.persistence.dao;

import com.leoalelui.ticketsystem.domain.dto.request.CategoryCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.CategoryResponseDTO;
import com.leoalelui.ticketsystem.persistence.entity.CategoryEntity;
import com.leoalelui.ticketsystem.persistence.mapper.CategoryMapper;
import com.leoalelui.ticketsystem.persistence.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * @author: alej0nt
 */
@Repository
@RequiredArgsConstructor
public class CategoryDAO {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryResponseDTO save(CategoryCreateDTO category) {
        CategoryEntity c = categoryMapper.toEntity(category);
        categoryRepository.save(c);
        return categoryMapper.toResponseDTO(c);
    }
}
