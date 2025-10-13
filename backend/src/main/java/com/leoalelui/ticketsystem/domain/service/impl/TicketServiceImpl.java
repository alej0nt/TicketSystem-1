package com.leoalelui.ticketsystem.domain.service.impl;

import com.leoalelui.ticketsystem.domain.dto.request.NotificationCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.request.TicketCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.request.TicketRecordCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.request.TicketUpdateStateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.CommentResponseDTO;
import com.leoalelui.ticketsystem.domain.dto.response.TicketRecordResponseDTO;
import com.leoalelui.ticketsystem.domain.dto.response.TicketResponseDTO;
import com.leoalelui.ticketsystem.domain.exception.InvalidStateException;
import com.leoalelui.ticketsystem.domain.exception.ResourceNotFoundException;
import com.leoalelui.ticketsystem.domain.service.NotificationService;
import com.leoalelui.ticketsystem.domain.service.TicketRecordService;
import com.leoalelui.ticketsystem.domain.service.TicketService;
import com.leoalelui.ticketsystem.persistence.dao.*;
import com.leoalelui.ticketsystem.persistence.enums.State;
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
        
        validateStateTransition(currentTicket.getState(), updateStateDTO.getState());
        
        createStateChangeRecord(currentTicket, updateStateDTO.getState());

        TicketResponseDTO ticketUpdated = updateTicketState(id, updateStateDTO);
        notificationService.create(new NotificationCreateDTO("El estado del tiquete: '" + currentTicket.getTitle() + "' acaba de ser actualizado a '" + ticketUpdated.getState() + "'", currentTicket.getEmployeeId()));

        return ticketUpdated;
    }

    @Override
    @Transactional
    public void deleteTicket(Long id) {
        validateTicketExists(id);
        ticketDAO.deleteById(id);
    }

    @Override
    public TicketResponseDTO getTicketById(Long id) {
        return ticketDAO.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Tiquete no encontrado con ID: " + id));
    }

    @Override
    public List<TicketResponseDTO> getAllTickets() {
        return ticketDAO.findAll();
    }

    @Override
    public List<TicketResponseDTO> getTicketsByState(State state) {
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
            throw new ResourceNotFoundException("Tiquete no encontrado con ID: " + ticketId);
        }
    }

    private void createStateChangeRecord(TicketResponseDTO ticket, State newState) {
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

    private void validateStateTransition(State currentState, State newState) {
        if (currentState.equals(newState)) {
            throw new InvalidStateException("El tiquete ya está en el estado: " + currentState.getDisplayName());
        }

        switch (currentState) {
            case ABIERTO:
                if (newState != State.EN_PROGRESO) {
                    throw new InvalidStateException(
                        "Desde estado '" + currentState.getDisplayName() + "' solo se puede cambiar a 'En progreso'. " +
                        "Estado solicitado: '" + newState.getDisplayName() + "'"
                    );
                }
                break;
                
            case EN_PROGRESO:
                if (newState != State.RESUELTO && newState != State.ABIERTO) {
                    throw new InvalidStateException(
                        "Desde estado '" + currentState.getDisplayName() + "' solo se puede cambiar a 'Resuelto' o 'Abierto'. " +
                        "Estado solicitado: '" + newState.getDisplayName() + "'"
                    );
                }
                break;
                
            case RESUELTO:
                if (newState != State.CERRADO && newState != State.EN_PROGRESO) {
                    throw new InvalidStateException(
                        "Desde estado '" + currentState.getDisplayName() + "' solo se puede cambiar a 'Cerrado' o 'En progreso'. " +
                        "Estado solicitado: '" + newState.getDisplayName() + "'"
                    );
                }
                break;
                
            case CERRADO:
                if (newState != State.RESUELTO) {
                    throw new InvalidStateException(
                        "Desde estado '" + currentState.getDisplayName() + "' solo se puede reabrir a 'Resuelto'. " +
                        "Estado solicitado: '" + newState.getDisplayName() + "'"
                    );
                }
                break;
                
            default:
                throw new InvalidStateException("Estado actual no reconocido: " + currentState);
        }
    }
}
