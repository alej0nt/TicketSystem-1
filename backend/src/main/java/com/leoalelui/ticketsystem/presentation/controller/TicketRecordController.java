package com.leoalelui.ticketsystem.presentation.controller;

import com.leoalelui.ticketsystem.domain.dto.request.TicketRecordCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.TicketRecordResponseDTO;
// import com.leoalelui.ticketsystem.domain.service.TicketRecordService;
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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller para manejar los registros (historial) de cambios de estado de
 * tickets.
 *
 * @author Leonardo Argoty
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/ticket-records")
@Tag(name = "Cambios de estado de ticket", description = "Operaciones para manejar el historial (registros) de cambios de estado de tickets")
public class TicketRecordController {

    // private final TicketRecordService ticketRecordService;
    @Operation(summary = "Crear registro de ticket", description = "Registra un cambio de estado en un ticket (historial).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Registro creado exitosamente",
                content = @Content(schema = @Schema(implementation = TicketRecordResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o request mal formado"),
        @ApiResponse(responseCode = "401", description = "No autorizado"),
        @ApiResponse(responseCode = "403", description = "Prohibido - rol insuficiente")
    })
    @PostMapping
    public ResponseEntity<TicketRecordResponseDTO> createTicketRecord(
            @Valid @RequestBody TicketRecordCreateDTO ticketRecordCreateDTO) {

        // TicketRecordResponseDTO created = ticketRecordService.create(ticketRecordCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @Operation(summary = "Listar registros por ticket", description = "Devuelve el historial de cambios asociados a un ticket específico.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registros del ticket",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = TicketRecordResponseDTO.class)))),
        @ApiResponse(responseCode = "404", description = "Ticket no encontrado"),
        @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<List<TicketRecordResponseDTO>> getRecordsByTicket(@PathVariable @Parameter(description = "ID del ticket con su historial a buscar") Long ticketId) {
        // List<TicketRecordResponseDTO> list = ticketRecordService.getByTicketId(ticketId);
        return ResponseEntity.ok(null);
    }

    @Operation(summary = "Listar registros por rango de fechas", description = "Devuelve los registros que ocurrieron entre dos fechas (inclusive).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registros filtrados por rango de fechas",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = TicketRecordResponseDTO.class)))),
        @ApiResponse(responseCode = "400", description = "Parámetros de fecha inválidos"),
        @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @GetMapping("/range")
    public ResponseEntity<List<TicketRecordResponseDTO>> getRecordsByDateRange(
            @RequestParam("from") @Parameter(description = "Fecha de inicio del rango (formato yyyy-MM-dd)") LocalDate from,
            @RequestParam("to") @Parameter(description = "Fecha de fin del rango (formato yyyy-MM-dd)") LocalDate to) {
        // List<TicketRecordResponseDTO> list = ticketRecordService.getByDateRange(from, to);
        return ResponseEntity.ok(null);
    }
}
