package com.leoalelui.ticketsystem.presentation.controller;

import com.leoalelui.ticketsystem.domain.dto.request.TicketCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.request.TicketUpdateStateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.CommentResponseDTO;
import com.leoalelui.ticketsystem.domain.dto.response.TicketRecordResponseDTO;
import com.leoalelui.ticketsystem.domain.dto.response.TicketResponseDTO;
import com.leoalelui.ticketsystem.domain.service.TicketService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/tickets")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketResponseDTO> createTicket(@Valid @RequestBody TicketCreateDTO ticketCreateDTO) {
        TicketResponseDTO ticketCreated = ticketService.createTicket(ticketCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketCreated); // 201 Created
    }

    @PutMapping("/{id}/state")
    public ResponseEntity<TicketResponseDTO> updateState(
            @PathVariable Long id,
            @RequestBody TicketUpdateStateDTO ticketUpdateStateDTO
    ) {
        TicketResponseDTO updated = ticketService.updateState(id, ticketUpdateStateDTO);
        return ResponseEntity.ok(updated); // 200 OK
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> getTicketById(@PathVariable Long id) {
        TicketResponseDTO ticket = ticketService.getTicketById(id);
        return ResponseEntity.ok(ticket); // 200 OK
    }

    @GetMapping
    public ResponseEntity<List<TicketResponseDTO>> getAllTickets() {
        List<TicketResponseDTO> tickets = ticketService.getAllTickets();
        return ResponseEntity.ok(tickets); // 200 OK
    }

    @GetMapping("/state/{state}")
    public ResponseEntity<List<TicketResponseDTO>> getTicketsByState(@PathVariable String state) {
        List<TicketResponseDTO> tickets = ticketService.getTicketsByState(state);
        return ResponseEntity.ok(tickets); // 200 OK
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentResponseDTO>> getAllCommentsByTicketId(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/ticket-records")
    public ResponseEntity<List<TicketRecordResponseDTO>> getAllTicketRecordsByTicketId(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }
}
