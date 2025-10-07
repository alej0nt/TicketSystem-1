package com.leoalelui.ticketsystem.persistence.dao;

import com.leoalelui.ticketsystem.domain.dto.request.CategoryCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.request.CategoryUpdateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.CategoryResponseDTO;
import com.leoalelui.ticketsystem.persistence.entity.CategoryEntity;
import com.leoalelui.ticketsystem.persistence.mapper.CategoryMapper;
import com.leoalelui.ticketsystem.persistence.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    public boolean existsById(Long id) {
        return categoryRepository.existsById(id);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    public boolean existsByName(String categoryName) {
        return categoryRepository.existsByName(categoryName);
    }

    public CategoryResponseDTO update(Long id, CategoryUpdateDTO categoryUpdateDTO) {
        CategoryEntity old = categoryRepository.findById(id).get();
        old.setName(categoryUpdateDTO.getName());
        old.setDescription(categoryUpdateDTO.getDescription());
        return categoryMapper.toResponseDTO(categoryRepository.save(old));
    }

    public CategoryResponseDTO getByName(String categoryName) {
        return categoryMapper.toResponseDTO(categoryRepository.findByName(categoryName));
    }

    public Optional<CategoryResponseDTO> getById(Long id) {
        return categoryRepository.findById(id).map(categoryMapper::toResponseDTO);
    }

    public List<CategoryResponseDTO> getAll() {
        return categoryRepository.findAll().
                stream().
                map(categoryMapper :: toResponseDTO).
                toList();
    }
}
