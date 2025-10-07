package com.leoalelui.ticketsystem.presentation.controller;

import com.leoalelui.ticketsystem.domain.dto.request.CategoryCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.request.CategoryUpdateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.CategoryResponseDTO;
import com.leoalelui.ticketsystem.domain.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.naming.ldap.HasControls;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/categories")
@Tag(name = "Categorias", description = "API para la gestión de categorias.")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(
            summary = "Obtener todas las categorias existentes",
            description = "Devuelve una lista con todas las categorias registradas en la base de datos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de categorías obtenida correctamente."),
            @ApiResponse(responseCode = "401", description = "Token inválido o no presente.")
    })
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @Operation(
            summary = "Obtener una categoría específica",
            description = "Devuelve una categoría según su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría encontrada correctamente."),
            @ApiResponse(responseCode = "404", description = "No se encontró la categoría con el ID especificado."),
            @ApiResponse(responseCode = "401", description = "Token inválido o no presente.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(
            @PathVariable @Parameter(description = "ID de la categoría a buscar") Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @Operation(
            summary = "Crear una nueva categoría",
            description = "Permite registrar una nueva categoría en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoría creada exitosamente."),
            @ApiResponse(responseCode = "409", description = "Conflicto, ya existe una categoría con ese nombre."),
            @ApiResponse(responseCode = "401", description = "Token inválido o no presente.")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> save(
            @RequestBody CategoryCreateDTO categoryCreateDTO) {
        return ResponseEntity.ok(categoryService.save(categoryCreateDTO));
    }

    @Operation(
            summary = "Eliminar una categoría",
            description = "Elimina una categoría existente mediante su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoría eliminada exitosamente."),
            @ApiResponse(responseCode = "404", description = "No se encontró la categoría con el ID especificado."),
            @ApiResponse(responseCode = "401", description = "Token inválido o no presente.")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable @Parameter(description = "ID de la categoría a eliminar") Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Actualizar una categoría",
            description = "Actualiza los datos de una categoría existente mediante su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría actualizada correctamente."),
            @ApiResponse(responseCode = "404", description = "No se encontró la categoría con el ID especificado."),
            @ApiResponse(responseCode = "401", description = "Token inválido o no presente.")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> update(
            @PathVariable @Parameter(description = "ID de la categoría a actualizar") Long id,
            @Valid @RequestBody CategoryUpdateDTO categoryUpdateDTO) {
        return ResponseEntity.ok(categoryService.update(id, categoryUpdateDTO));
    }
}
