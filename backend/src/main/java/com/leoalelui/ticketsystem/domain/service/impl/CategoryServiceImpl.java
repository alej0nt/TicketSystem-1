package com.leoalelui.ticketsystem.domain.service.impl;

import com.leoalelui.ticketsystem.domain.dto.CategoryCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.CategoryResponseDTO;
import com.leoalelui.ticketsystem.domain.service.CategoryService;
import com.leoalelui.ticketsystem.persistence.dao.CategoryDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryDAO categoryDAO;

    @Override
    public CategoryResponseDTO save(CategoryCreateDTO categoryCreateDTO) {
        return categoryDAO.save(categoryCreateDTO);
    }
}
