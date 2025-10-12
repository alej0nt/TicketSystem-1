package com.leoalelui.ticketsystem.presentation.controller;

import com.leoalelui.ticketsystem.domain.dto.request.AssignmentCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.AssignmentResponseDTO;
import com.leoalelui.ticketsystem.domain.service.AssignmentService;
//import com.leoalelui.ticketsystem.domain.service.AssignmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller para manejar las asignaciones que se hagan en tickets.
 *
 * @author Leonardo Argoty
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/assignments")
@Tag(name = "Asignaciones", description = "Operaciones para manejar asignaciones de tickets a empleados")
public class AssignmentController {

    private final AssignmentService assignmentService;

    
    @Operation(summary = "Crear asignación", description = "Crea una nueva asignación de un ticket a un empleado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Asignación creada exitosamente",
                content = @Content(schema = @Schema(implementation = AssignmentResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o request mal formado"),
        @ApiResponse(responseCode = "401", description = "No autorizado"),
        @ApiResponse(responseCode = "403", description = "Prohibido - rol insuficiente")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<AssignmentResponseDTO> createAssignment(
            @Valid @RequestBody AssignmentCreateDTO assignmentCreateDTO) {

        AssignmentResponseDTO created = assignmentService.create(assignmentCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Listar asignaciones por empleado agente", description = "Devuelve las asignaciones asociadas a un empleado agente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Asignaciones del empleado agente",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = AssignmentResponseDTO.class)))),
        @ApiResponse(responseCode = "404", description = "Agente no encontrado"),
        @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<AssignmentResponseDTO>> getAssignmentsByEmployee(@PathVariable @Parameter(description = "ID del empleado agente con sus asignaciones") Long employeeId,
    @RequestParam(value="query", required = false) @Parameter(description = "Parámetro de búsqueda opcional para filtrar asignaciones") String query) {
        List<AssignmentResponseDTO> list = assignmentService.getByEmployeeId(employeeId, query);
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Obtener asignación por ticket", description = "Devuelve la asignación actual asociada a un ticket.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Asignación encontrada",
                content = @Content(schema = @Schema(implementation = AssignmentResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Ticket o asignación no encontrada"),
        @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<AssignmentResponseDTO> getAssignmentByTicket(
            @PathVariable @Parameter(description = "ID del ticket para obtener su asignación actual") Long ticketId) {

        AssignmentResponseDTO dto = assignmentService.getByTicketId(ticketId);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Reasignar agente", description = "Permite cambiar el empleado asignado a un ticket existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Asignación reasignada exitosamente",
                content = @Content(schema = @Schema(implementation = AssignmentResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o request mal formado"),
        @ApiResponse(responseCode = "404", description = "Asignación o empleado no encontrado"),
        @ApiResponse(responseCode = "401", description = "No autorizado"),
        @ApiResponse(responseCode = "403", description = "Prohibido - rol insuficiente")
    })
    @PatchMapping("reassign/{ticketId}")
    public ResponseEntity<AssignmentResponseDTO> reassignEmployee(
            @PathVariable @Parameter(description = "ID de la asignación a actualizar") Long ticketId,
            @RequestParam("employeeId") @Parameter(description = "Nuevo ID del empleado agente") Long employeeId) {

        AssignmentResponseDTO updated = assignmentService.reassignEmployee(ticketId, employeeId);
        return ResponseEntity.ok(updated);
    }
}
