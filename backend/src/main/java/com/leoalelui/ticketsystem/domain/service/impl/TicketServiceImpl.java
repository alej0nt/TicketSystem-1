package com.leoalelui.ticketsystem.domain.service.impl;

import com.leoalelui.ticketsystem.domain.dto.request.NotificationCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.request.TicketCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.request.TicketRecordCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.request.TicketUpdateStateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.CommentResponseDTO;
import com.leoalelui.ticketsystem.domain.dto.response.TicketRecordResponseDTO;
import com.leoalelui.ticketsystem.domain.dto.response.TicketResponseDTO;
import com.leoalelui.ticketsystem.domain.exception.ResourceNotFoundException;
import com.leoalelui.ticketsystem.domain.service.NotificationService;
import com.leoalelui.ticketsystem.domain.service.TicketRecordService;
import com.leoalelui.ticketsystem.domain.service.TicketService;
import com.leoalelui.ticketsystem.persistence.dao.*;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketDAO ticketDAO;
    private final EmployeeDAO employeeDAO;
    private final CategoryDAO categoryDAO;
    private final CommentDAO commentDAO;

    private final TicketRecordService ticketRecordService;
    private final NotificationService notificationService;

    @Override
    @Transactional
    public TicketResponseDTO createTicket(TicketCreateDTO createDTO) {
        validateEmployeeExists(createDTO.getEmployeeId());
        validateCategoryExists(createDTO.getCategoryId());
        return ticketDAO.save(createDTO);
    }

    @Override
    @Transactional
    public TicketResponseDTO updateState(Long id, TicketUpdateStateDTO updateStateDTO) {
        TicketResponseDTO currentTicket = getTicketById(id);
        createStateChangeRecord(currentTicket, updateStateDTO.getState());

        TicketResponseDTO ticketActualizado = updateTicketState(id, updateStateDTO);
        notificationService.create(new NotificationCreateDTO("El estado del ticket: '" + currentTicket.getTitle() + "' acaba de ser actualizado a '" + ticketActualizado.getState() + "'", currentTicket.getEmployeeId()));

        return ticketActualizado;
    }

    @Override
    public void deleteTicket(Long id) {
        if (!ticketDAO.existsById(id)) {
            throw new ResourceNotFoundException("Tiquete no encontrado.");
        }
        ticketDAO.deleteById(id);
    }

    @Override
    public TicketResponseDTO getTicketById(Long id) {
        return ticketDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tiquete no encontrado"));
    }

    @Override
    public List<TicketResponseDTO> getAllTickets() {
        return ticketDAO.findAll();
    }

    @Override
    public List<TicketResponseDTO> getTicketsByState(String state) {
        return ticketDAO.findByState(state);
    }

    @Override
    public List<CommentResponseDTO> getAllCommentsByTicketId(Long ticketId) {
        validateTicketExists(ticketId);
        return commentDAO.findAllByTicketId(ticketId);
    }

    @Override
    public List<TicketRecordResponseDTO> getAllTicketRecordsByTicketId(Long ticketId, LocalDate from, LocalDate to) {
        validateTicketExists(ticketId);
        return ticketRecordService.getByDateRange(ticketId, from, to);
    }

    private void validateEmployeeExists(Long employeeId) {
        if (!employeeDAO.existsById(employeeId)) {
            throw new ResourceNotFoundException("Empleado no encontrado con ID: " + employeeId);
        }
    }

    private void validateCategoryExists(Long categoryId) {
        if (!categoryDAO.existsById(categoryId)) {
            throw new ResourceNotFoundException("Categoría no encontrada con ID: " + categoryId);
        }
    }

    private void validateTicketExists(Long ticketId) {
        if (!ticketDAO.existsById(ticketId)) {
            throw new ResourceNotFoundException("Ticket no encontrado con ID: " + ticketId);
        }
    }

    private void createStateChangeRecord(TicketResponseDTO ticket, String newState) {
        TicketRecordCreateDTO recordDTO = new TicketRecordCreateDTO(
                ticket.getId(),
                ticket.getState(),
                newState
        );
        ticketRecordService.create(recordDTO);
    }

    private TicketResponseDTO updateTicketState(Long id, TicketUpdateStateDTO updateStateDTO) {
        return ticketDAO.updateState(id, updateStateDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Error al actualizar ticket con ID: " + id));
    }
}
