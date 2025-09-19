package com.leoalelui.ticketsystem.domain.service;

import com.leoalelui.ticketsystem.domain.dto.request.TicketCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.request.TicketUpdateStateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.CommentResponseDTO;
import com.leoalelui.ticketsystem.domain.dto.response.TicketRecordResponseDTO;
import com.leoalelui.ticketsystem.domain.dto.response.TicketResponseDTO;
import java.time.LocalDate;

import java.util.List;

public interface TicketService {
    TicketResponseDTO createTicket(TicketCreateDTO ticketCreateDTO);
    TicketResponseDTO updateState(Long id, TicketUpdateStateDTO ticketUpdateStateDTO);
    void deleteTicket(Long id);
    TicketResponseDTO getTicketById(Long id);
    List<TicketResponseDTO> getAllTickets();
    List<TicketResponseDTO> getTicketsByState(String state);
    List<CommentResponseDTO> getAllCommentsByTicketId(Long id);
    List<TicketRecordResponseDTO> getAllTicketRecordsByTicketId(Long id, LocalDate from, LocalDate to);
}
