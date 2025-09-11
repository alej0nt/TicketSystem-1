package com.leoalelui.ticketsystem.presentation.controller;

import com.leoalelui.ticketsystem.domain.dto.request.CategoryCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.request.CategoryUpdateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.CategoryResponseDTO;
import com.leoalelui.ticketsystem.domain.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories(
            @Parameter(hidden = true) @RequestHeader(name = "Authorization") String token) {
        return ResponseEntity.ok(null);
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
            @Parameter(hidden = true) @RequestHeader(name = "Authorization") String token,
            @PathVariable @Parameter(description = "ID de la categoría a buscar") Long id) {
        return ResponseEntity.ok(null);
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
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> save(
            @Parameter(hidden = true) @RequestHeader(name = "Authorization") String token,
            @Valid @RequestBody CategoryCreateDTO categoryCreateDTO) {
        return ResponseEntity.status(201).body(null);
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(hidden = true) @RequestHeader(name = "Authorization") String token,
            @PathVariable @Parameter(description = "ID de la categoría a eliminar") Long id) {
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
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> update(
            @Parameter(hidden = true) @RequestHeader(name = "Authorization") String token,
            @PathVariable @Parameter(description = "ID de la categoría a actualizar") Long id,
            @Valid @RequestBody CategoryUpdateDTO categoryUpdateDTO) {
        return ResponseEntity.ok(null);
    }
}
