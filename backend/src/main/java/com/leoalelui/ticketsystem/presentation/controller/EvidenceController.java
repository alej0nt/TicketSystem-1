package com.leoalelui.ticketsystem.presentation.controller;

import com.leoalelui.ticketsystem.domain.dto.request.EvidenceCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.EvidenceResponseDTO;
import com.leoalelui.ticketsystem.domain.service.EvidenceService;
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

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/evidencias")
@Tag(name = "Evidencias", description = "API para la gestión de evidencias (imágenes) asociadas a los tickets.")
public class EvidenceController {

    private final EvidenceService evidenceService;
    @Operation(summary = "Obtener todas las evidencias de un ticket",
            description = "Devuelve una lista con todas las evidencias asociadas a un ticket específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se devuelve la lista de evidencias exitosamente."),
            @ApiResponse(responseCode = "204", description = "El ticket no tiene evidencias registradas."),
            @ApiResponse(responseCode = "401", description = "Token inválido o ausente.")
    })
    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<List<EvidenceResponseDTO>> getAllByTicket(
            @Parameter(description = "ID del ticket") @PathVariable Long ticketId) {
        return ResponseEntity.ok(evidenceService.findAllByTicketId(ticketId));
    }

    @Operation(summary = "Obtener una evidencia por su ID",
            description = "Devuelve la información de una evidencia específica mediante su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se devuelve la evidencia correctamente."),
            @ApiResponse(responseCode = "404", description = "No se encontró la evidencia con el ID especificado."),
            @ApiResponse(responseCode = "401", description = "Token inválido o ausente.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EvidenceResponseDTO> getById(
            @Parameter(description = "ID de la evidencia") @PathVariable Long id) {
        return ResponseEntity.ok(evidenceService.findById(id));
    }

    @Operation(summary = "Crear una nueva evidencia",
            description = "Crea una nueva evidencia (imagen) y la asocia a un ticket existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "La evidencia fue creada exitosamente."),
            @ApiResponse(responseCode = "400", description = "Datos de la solicitud inválidos."),
            @ApiResponse(responseCode = "401", description = "Token inválido o ausente.")
    })
    @PostMapping
    public ResponseEntity<EvidenceResponseDTO> create(
            @Valid @RequestBody EvidenceCreateDTO evidenceCreateDTO) {
        return ResponseEntity.status(201).body(evidenceService.save(evidenceCreateDTO));
    }

    @Operation(summary = "Eliminar una evidencia",
            description = "Elimina una evidencia específica mediante su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "La evidencia fue eliminada correctamente."),
            @ApiResponse(responseCode = "404", description = "No se encontró la evidencia con el ID especificado."),
            @ApiResponse(responseCode = "401", description = "Token inválido o ausente.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la evidencia") @PathVariable Long id) {
        evidenceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
