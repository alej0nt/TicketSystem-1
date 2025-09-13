package com.leoalelui.ticketsystem.presentation.controller;

import com.leoalelui.ticketsystem.domain.dto.request.CommentCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.request.CommentUpdateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.CommentResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/comments")
@Tag(name = "Comentarios", description = "API para la gestión de comentarios en los tickets.")
public class CommentController {

    @Operation(summary = "Obtener todos los comentarios de un ticket",
            description = "Devuelve una lista con todos los comentarios asociados a un ticket específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se devuelve la lista de comentarios exitosamente."),
            @ApiResponse(responseCode = "204", description = "El ticket no tiene comentarios registrados."),
            @ApiResponse(responseCode = "401", description = "Token inválido o ausente.")
    })
    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<List<CommentResponseDTO>> getAllByTicket(
            @Parameter(description = "ID del ticket") @PathVariable Long ticketId,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(null);
    }

    @Operation(summary = "Obtener un comentario por su ID",
            description = "Devuelve la información de un comentario específico mediante su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se devuelve el comentario correctamente."),
            @ApiResponse(responseCode = "404", description = "No se encontró el comentario con el ID especificado."),
            @ApiResponse(responseCode = "401", description = "Token inválido o ausente.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDTO> getById(
            @Parameter(description = "ID del comentario") @PathVariable Long id,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(null);
    }

    @Operation(summary = "Crear un nuevo comentario",
            description = "Agrega un nuevo comentario a un ticket existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "El comentario fue creado exitosamente."),
            @ApiResponse(responseCode = "400", description = "Datos de la solicitud inválidos."),
            @ApiResponse(responseCode = "401", description = "Token inválido o ausente.")
    })
    @PostMapping
    public ResponseEntity<CommentResponseDTO> create(
            @Valid @RequestBody CommentCreateDTO commentCreateDTO,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.status(201).body(null);
    }

    @Operation(summary = "Actualizar un comentario",
            description = "Edita el contenido de un comentario existente mediante su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El comentario fue actualizado correctamente."),
            @ApiResponse(responseCode = "404", description = "No se encontró el comentario con el ID especificado."),
            @ApiResponse(responseCode = "400", description = "Datos de la solicitud inválidos."),
            @ApiResponse(responseCode = "401", description = "Token inválido o ausente.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDTO> update(
            @Parameter(description = "ID del comentario") @PathVariable Long id,
            @Valid @RequestBody CommentUpdateDTO commentUpdateDTO,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(null);
    }

    @Operation(summary = "Eliminar un comentario",
            description = "Elimina un comentario específico mediante su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "El comentario fue eliminado correctamente."),
            @ApiResponse(responseCode = "404", description = "No se encontró el comentario con el ID especificado."),
            @ApiResponse(responseCode = "401", description = "Token inválido o ausente.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del comentario") @PathVariable Long id,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.noContent().build();
    }
}
