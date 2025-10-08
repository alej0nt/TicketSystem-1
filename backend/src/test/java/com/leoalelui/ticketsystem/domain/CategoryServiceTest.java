package com.leoalelui.ticketsystem.domain;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.leoalelui.ticketsystem.domain.dto.request.CategoryCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.request.CategoryUpdateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.CategoryResponseDTO;
import com.leoalelui.ticketsystem.domain.exception.ResourceAlreadyExistsException;
import com.leoalelui.ticketsystem.domain.exception.ResourceNotFoundException;
import com.leoalelui.ticketsystem.domain.service.impl.CategoryServiceImpl;
import com.leoalelui.ticketsystem.persistence.dao.CategoryDAO;

@ExtendWith(MockitoExtension.class)
@DisplayName("CategoryService - Unit Test")
public class CategoryServiceTest {

    @Mock
    private CategoryDAO categoryDAO;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private CategoryCreateDTO createDTO;
    private CategoryUpdateDTO updateDTO;
    private CategoryResponseDTO responseDTO;

    private Long validId;

    @BeforeEach
    void setUp() {
        validId = 1L;

        createDTO = new CategoryCreateDTO();
        createDTO.setName("Tecnología");

        updateDTO = new CategoryUpdateDTO();
        updateDTO.setName("Hogar");

        responseDTO = new CategoryResponseDTO(validId, "Tecnología", "Categoría de tecnología y software");
    }

    // ==================== SAVE TESTS ====================
    @Test
    @DisplayName("SAVE - Debe guardar una categoría cuando el nombre no existe")
    void save_ValidName_ShouldSaveCategory() {
        when(categoryDAO.existsByName(createDTO.getName())).thenReturn(false);
        when(categoryDAO.save(createDTO)).thenReturn(responseDTO);

        CategoryResponseDTO result = categoryService.save(createDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(validId);
        assertThat(result.getName()).isEqualTo("Tecnología");
        assertThat(result.getDescription()).isEqualTo("Categoría de tecnología y software");

        verify(categoryDAO, times(1)).existsByName(createDTO.getName());
        verify(categoryDAO, times(1)).save(createDTO);
    }

    @Test
    @DisplayName("SAVE - Debe lanzar ResourceAlreadyExistsException si el nombre ya existe")
    void save_ExistingName_ShouldThrowException() {
        when(categoryDAO.existsByName(createDTO.getName())).thenReturn(true);

        assertThatThrownBy(() -> categoryService.save(createDTO))
                .isInstanceOf(ResourceAlreadyExistsException.class)
                .hasMessageContaining("Ya existe una categoría con nombre");

        verify(categoryDAO, times(1)).existsByName(createDTO.getName());
        verify(categoryDAO, never()).save(any());
    }

    // ==================== DELETE TESTS ====================
    @Test
    @DisplayName("DELETE - Debe eliminar categoría existente")
    void delete_ExistingId_ShouldDeleteCategory() {
        when(categoryDAO.existsById(validId)).thenReturn(true);

        categoryService.delete(validId);

        verify(categoryDAO, times(1)).existsById(validId);
        verify(categoryDAO, times(1)).delete(validId);
    }

    @Test
    @DisplayName("DELETE - Debe lanzar ResourceNotFoundException si el ID no existe")
    void delete_NonExistentId_ShouldThrowException() {
        when(categoryDAO.existsById(validId)).thenReturn(false);

        assertThatThrownBy(() -> categoryService.delete(validId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("No se ha encontrado la categoría con id");

        verify(categoryDAO, times(1)).existsById(validId);
        verify(categoryDAO, never()).delete(any());
    }

    // ==================== UPDATE TESTS ====================
    @Test
    @DisplayName("UPDATE - Debe actualizar categoría cuando ID existe y nombre no está tomado")
    void update_ValidData_ShouldUpdateCategory() {
        CategoryResponseDTO updatedResponse = new CategoryResponseDTO(validId, "Hogar", "Categoría del hogar");

        when(categoryDAO.existsById(validId)).thenReturn(true);
        when(categoryDAO.getByName(updateDTO.getName())).thenReturn(null);
        when(categoryDAO.update(validId, updateDTO)).thenReturn(updatedResponse);

        CategoryResponseDTO result = categoryService.update(validId, updateDTO);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Hogar");
        assertThat(result.getDescription()).isEqualTo("Categoría del hogar");

        verify(categoryDAO, times(1)).existsById(validId);
        verify(categoryDAO, times(1)).update(validId, updateDTO);
    }

    @Test
    @DisplayName("UPDATE - Debe lanzar ResourceNotFoundException si el ID no existe")
    void update_NonExistentId_ShouldThrowException() {
        when(categoryDAO.existsById(validId)).thenReturn(false);

        assertThatThrownBy(() -> categoryService.update(validId, updateDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("No se ha encontrado la categoría con id");

        verify(categoryDAO, times(1)).existsById(validId);
        verify(categoryDAO, never()).update(any(), any());
    }

    @Test
    @DisplayName("UPDATE - Debe lanzar ResourceAlreadyExistsException si el nuevo nombre pertenece a otra categoría")
    void update_NameTakenByAnother_ShouldThrowException() {
        CategoryResponseDTO another = new CategoryResponseDTO(2L, "Hogar", "Otra categoría");
        when(categoryDAO.existsById(validId)).thenReturn(true);
        when(categoryDAO.getByName(updateDTO.getName())).thenReturn(another);

        assertThatThrownBy(() -> categoryService.update(validId, updateDTO))
                .isInstanceOf(ResourceAlreadyExistsException.class)
                .hasMessageContaining("Ya existe otra categoría con nombre");

        verify(categoryDAO, times(1)).existsById(validId);
        verify(categoryDAO, times(1)).getByName(updateDTO.getName());
        verify(categoryDAO, never()).update(any(), any());
    }

    @Test
    @DisplayName("UPDATE - Debe permitir actualizar con el mismo nombre")
    void update_SameNameSameId_ShouldUpdateCategory() {
        CategoryResponseDTO sameCategory = new CategoryResponseDTO(validId, "Tecnología", "Desc");
        when(categoryDAO.existsById(validId)).thenReturn(true);
        when(categoryDAO.getByName(updateDTO.getName())).thenReturn(sameCategory);
        when(categoryDAO.update(validId, updateDTO)).thenReturn(sameCategory);

        CategoryResponseDTO result = categoryService.update(validId, updateDTO);

        assertThat(result).isNotNull();
        verify(categoryDAO, times(1)).update(validId, updateDTO);
    }
    // ==================== FIND BY ID TESTS ====================
    @Test
    @DisplayName("FIND BY ID - Debe retornar categoría existente")
    void findById_ExistingId_ShouldReturnCategory() {
        when(categoryDAO.getById(validId)).thenReturn(Optional.of(responseDTO));

        CategoryResponseDTO result = categoryService.findById(validId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(validId);
        assertThat(result.getName()).isEqualTo("Tecnología");

        verify(categoryDAO, times(1)).getById(validId);
    }

    @Test
    @DisplayName("FIND BY ID - Debe lanzar ResourceNotFoundException si no se encuentra")
    void findById_NonExistentId_ShouldThrowException() {
        when(categoryDAO.getById(validId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.findById(validId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("No se encontro la categoria con el id");

        verify(categoryDAO, times(1)).getById(validId);
    }

    // ==================== FIND ALL TESTS ====================
    @Test
    @DisplayName("FIND ALL - Debe retornar lista de categorías")
    void findAll_ShouldReturnCategoryList() {
        List<CategoryResponseDTO> expectedList = List.of(responseDTO);
        when(categoryDAO.getAll()).thenReturn(expectedList);

        List<CategoryResponseDTO> result = categoryService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result).isEqualTo(expectedList);

        verify(categoryDAO, times(1)).getAll();
    }
}
