package com.leoalelui.ticketsystem.domain.service;

import com.leoalelui.ticketsystem.domain.dto.CategoryCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.CategoryResponseDTO;

/**
 * @author: alej0nt
 */
public interface CategoryService {
    CategoryResponseDTO save (CategoryCreateDTO categoryCreateDTO);
}
