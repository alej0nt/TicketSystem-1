package com.leoalelui.ticketsystem.domain.service.impl;

import com.leoalelui.ticketsystem.domain.dto.request.CategoryCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.request.CategoryUpdateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.CategoryResponseDTO;
import com.leoalelui.ticketsystem.domain.exception.ResourceAlreadyExistsException;
import com.leoalelui.ticketsystem.domain.exception.ResourceNotFoundException;
import com.leoalelui.ticketsystem.domain.service.CategoryService;
import com.leoalelui.ticketsystem.persistence.dao.CategoryDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryDAO categoryDAO;

    @Override
    public CategoryResponseDTO save(CategoryCreateDTO dto) {
        ensureNameNotTaken(dto.getName());
        return categoryDAO.save(dto);
    }

    @Override
    public void delete(Long id) {
        ensureExists(id);
        categoryDAO.delete(id);
    }

    @Override
    public CategoryResponseDTO update(Long id, CategoryUpdateDTO categoryUpdateDTO) {
        ensureExists(id);
        ensureNameNotTakenForUpdate(id, categoryUpdateDTO.getName());
        return categoryDAO.update(id, categoryUpdateDTO);
    }

    @Override
    public CategoryResponseDTO findById(Long id) {
        return categoryDAO.getById(id).orElseThrow(() -> new ResourceNotFoundException("No se encontro la categoria con el id: " + id));
    }

    @Override
    public List<CategoryResponseDTO> findAll() {
        return categoryDAO.getAll();
    }


    // private methods

    private void ensureExists(Long id) {
        if (!categoryDAO.existsById(id)) {
            throw new ResourceNotFoundException("No se ha encontrado la categoría con id: " + id);
        }
    }

    private void ensureNameNotTaken(String name) {
        if (categoryDAO.existsByName(name)) {
            throw new ResourceAlreadyExistsException("Ya existe una categoría con nombre: " + name);
        }
    }

    private void ensureNameNotTakenForUpdate(Long id, String name) {
        CategoryResponseDTO named = categoryDAO.getByName(name);
        if (named != null && !named.getId().equals(id)) {
            throw new ResourceAlreadyExistsException("Ya existe otra categoría con nombre: " + name);
        }
    }
}