package com.leoalelui.ticketsystem.presentation.controller;

import com.leoalelui.ticketsystem.domain.dto.request.AssignmentCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.AssignmentResponseDTO;
//import com.leoalelui.ticketsystem.domain.service.AssignmentService;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author Leonardo Argoty
 */

@RestController
@AllArgsConstructor
@RequestMapping("api/assignments")
@Tag(name = "Assignments", description = "Operaciones para manejar asignaciones de tickets a empleados")
public class AssignmentController {

    //private final AssignmentService assignmentService;

    @Operation(summary = "Crear asignación", description = "Crea una nueva asignación de un ticket a un empleado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Asignación creada exitosamente",
                    content = @Content(schema = @Schema(implementation = AssignmentResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o request mal formado"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "403", description = "Prohibido - rol insuficiente")
    })
    @PostMapping
    public ResponseEntity<AssignmentResponseDTO> createAssignment(
            @Valid @RequestBody AssignmentCreateDTO assignmentCreateDTO) {

        //AssignmentResponseDTO created = assignmentService.create(assignmentCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @Operation(summary = "Obtener asignación por ID", description = "Recupera una asignación por su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Asignación encontrada",
                    content = @Content(schema = @Schema(implementation = AssignmentResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Asignación no encontrada"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AssignmentResponseDTO> getAssignmentById(@PathVariable Long id) {
        //AssignmentResponseDTO dto = assignmentService.getById(id);
        return ResponseEntity.ok(null);
    }

    @Operation(summary = "Listar todas las asignaciones", description = "Devuelve todas las asignaciones registradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de asignaciones",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AssignmentResponseDTO.class)))),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @GetMapping
    public ResponseEntity<List<AssignmentResponseDTO>> getAllAssignments() {
        //List<AssignmentResponseDTO> list = assignmentService.getAll();
        return ResponseEntity.ok(null);
    }

    @Operation(summary = "Listar asignaciones por empleado", description = "Devuelve las asignaciones asociadas a un empleado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Asignaciones del empleado",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AssignmentResponseDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<AssignmentResponseDTO>> getAssignmentsByEmployee(@PathVariable Long employeeId) {
        //List<AssignmentResponseDTO> list = assignmentService.getByEmployeeId(employeeId);
        return ResponseEntity.ok(null);
    }

    @Operation(summary = "Listar asignaciones por ticket", description = "Devuelve las asignaciones (histórico) relacionadas a un ticket.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Asignaciones del ticket",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AssignmentResponseDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Ticket no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<List<AssignmentResponseDTO>> getAssignmentsByTicket(@PathVariable Long ticketId) {
        //List<AssignmentResponseDTO> list = assignmentService.getByTicketId(ticketId);
        return ResponseEntity.ok(null);
    }

    @Operation(summary = "Eliminar asignación", description = "Elimina una asignación por su ID (uso administrativo).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Asignación eliminada exitosamente (sin contenido)"),
            @ApiResponse(responseCode = "404", description = "Asignación no encontrada"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "403", description = "Prohibido - rol insuficiente")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        //assignmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

