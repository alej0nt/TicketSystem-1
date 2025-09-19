package com.leoalelui.ticketsystem.presentation.controller;

import com.leoalelui.ticketsystem.domain.dto.request.CommentCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.CommentResponseDTO;
import com.leoalelui.ticketsystem.domain.service.CommentService;
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

    private final CommentService commentService;
    @Operation(summary = "Obtener todos los comentarios de un ticket",
            description = "Devuelve una lista con todos los comentarios asociados a un ticket específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se devuelve la lista de comentarios exitosamente."),
            @ApiResponse(responseCode = "204", description = "El ticket no tiene comentarios registrados."),
            @ApiResponse(responseCode = "401", description = "Token inválido o ausente.")
    })
    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<List<CommentResponseDTO>> getAllByTicket(
            @Parameter(description = "ID del ticket") @PathVariable Long ticketId) {
        return ResponseEntity.ok(commentService.findAllByTicketId(ticketId));
    }

    @Operation(summary = "Crear un nuevo comentario",
            description = "Agrega un nuevo comentario a un ticket existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "El comentario fue creado exitosamente."),
            @ApiResponse(responseCode = "400", description = "Datos de la solicitud inválidos."),
            @ApiResponse(responseCode = "401", description = "Token inválido o ausente.")
    })
    @PostMapping
    public ResponseEntity<CommentResponseDTO> save(
            @Valid @RequestBody CommentCreateDTO commentCreateDTO) {
        return ResponseEntity.ok(commentService.save(commentCreateDTO));
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
            @Parameter(description = "ID del comentario") @PathVariable Long id) {
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
