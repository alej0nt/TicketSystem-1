package com.leoalelui.ticketsystem.presentation.controller;

import com.leoalelui.ticketsystem.domain.dto.request.TicketCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.request.TicketUpdateStateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.CommentResponseDTO;
import com.leoalelui.ticketsystem.domain.dto.response.TicketRecordResponseDTO;
import com.leoalelui.ticketsystem.domain.dto.response.TicketResponseDTO;
import com.leoalelui.ticketsystem.domain.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/tickets")
@Tag(name = "Tiquetes", description = "API para la gestión de tiquetes.")
public class TicketController {

    private final TicketService ticketService;

    @Operation(summary = "Crear un nuevo tiquete.", description = "Registra un nuevo tiquete en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Tiquete creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<TicketResponseDTO> createTicket(@Valid @RequestBody TicketCreateDTO ticketCreateDTO) {
        TicketResponseDTO ticketCreated = ticketService.createTicket(ticketCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketCreated); // 201 Created
    }

    @Operation(summary = "Actualizar estado de un tiquete.", description = "Modifica el estado de un tiquete existente (ej: ABIERTO, EN_PROCESO, CERRADO).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estado del tiquete actualizado."),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos."),
        @ApiResponse(responseCode = "404", description = "Tiquete no encontrado.")
    })
    @PutMapping("/{id}/state")
    public ResponseEntity<TicketResponseDTO> updateState(
            @PathVariable Long id,
            @RequestBody TicketUpdateStateDTO ticketUpdateStateDTO
    ) {
        TicketResponseDTO updated = ticketService.updateState(id, ticketUpdateStateDTO);
        return ResponseEntity.ok(updated); // 200 OK
    }

    @Operation(summary = "Eliminar un tiquete.", description = "Elimina un tiquete por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Tiquete eliminado exitosamente."),
        @ApiResponse(responseCode = "404", description = "Tiquete no encontrado.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @Operation(summary = "Buscar un tiquete por ID.", description = "Obtiene los datos de un tiquete específico.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tiquete encontrado."),
        @ApiResponse(responseCode = "404", description = "Tiquete no encontrado.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> getTicketById(@PathVariable Long id) {
        TicketResponseDTO ticket = ticketService.getTicketById(id);
        return ResponseEntity.ok(ticket); // 200 OK
    }

    @Operation(summary = "Listar todos los tickets.", description = "Obtiene una lista de todos los tiquetes registrados en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente.")
    })
    @GetMapping
    public ResponseEntity<List<TicketResponseDTO>> getAllTickets() {
        List<TicketResponseDTO> tickets = ticketService.getAllTickets();
        return ResponseEntity.ok(tickets); // 200 OK
    }

    @Operation(summary = "Listar tickets por estado", description = "Obtiene una lista de tiquetes filtrados por estado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente."),
        @ApiResponse(responseCode = "404", description = "No se encontraron tiquetes con ese estado.")
    })
    @GetMapping("/state/{state}")
    public ResponseEntity<List<TicketResponseDTO>> getTicketsByState(@PathVariable String state) {
        List<TicketResponseDTO> tickets = ticketService.getTicketsByState(state);
        return ResponseEntity.ok(tickets); // 200 OK
    }

    @Operation(summary = "Listar comentarios de un ticket", description = "Obtiene todos los comentarios asociados a un tiquete.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comentarios obtenidos exitosamente."),
        @ApiResponse(responseCode = "404", description = "Tiquete no encontrado.")
    })
    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentResponseDTO>> getAllCommentsByTicketId(@PathVariable Long id) {
        List<CommentResponseDTO> comments = ticketService.getAllCommentsByTicketId(id);
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "Listar registros de un tiquete", description = "Obtiene el historial de registros de un tiquete.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Historial de registros obtenido exitosamente."),
        @ApiResponse(responseCode = "400", description = "Fechas invalidas."),
        @ApiResponse(responseCode = "404", description = "Tiquete no encontrado.")
    })
    @GetMapping("/{id}/tickets-record")
    public ResponseEntity<List<TicketRecordResponseDTO>> getAllTicketRecordsByTicketId(@PathVariable Long id, @RequestParam(value = "from", required = false) @Parameter(description = "Fecha de inicio del rango (formato yyyy-MM-dd)") LocalDate from,
            @RequestParam(value = "to", required = false) @Parameter(description = "Fecha de fin del rango (formato yyyy-MM-dd)") LocalDate to) {
        List<TicketRecordResponseDTO> ticketsRecord = ticketService.getAllTicketRecordsByTicketId(id, from, to);
        return ResponseEntity.ok(ticketsRecord);
    }
}
