package com.leoalelui.ticketsystem.domain.service.impl;

import com.leoalelui.ticketsystem.domain.dto.request.TicketRecordCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.TicketRecordResponseDTO;
import com.leoalelui.ticketsystem.domain.service.TicketRecordService;
import com.leoalelui.ticketsystem.persistence.dao.TicketDAO;
import com.leoalelui.ticketsystem.persistence.dao.TicketRecordDAO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Implementación del servicio para manejar registros de cambios en tickets.
 * @author Leonardo Argoty
 */
@RequiredArgsConstructor
@Service
public class TicketRecordServiceImpl implements TicketRecordService {

    private final TicketRecordDAO ticketRecordDAO;
    private final TicketDAO ticketDAO;

    @Override
    public TicketRecordResponseDTO create(TicketRecordCreateDTO ticketRecordCreateDTO) {
        Long ticketId = ticketRecordCreateDTO.getTicketId();

        validateTicketEntityById(ticketId);

        // Validar cambio de estado distinto
        if (ticketRecordCreateDTO.getPreviousState().equalsIgnoreCase(ticketRecordCreateDTO.getNextState())) {
            throw new IllegalArgumentException("El ticket no puede cambiar al mismo estado que el anterior.");
        }

        return ticketRecordDAO.create(ticketRecordCreateDTO);
    }

    @Override
    public List<TicketRecordResponseDTO> getByTicketId(Long ticketId) {
        validateTicketEntityById(ticketId);

        return ticketRecordDAO.findTicketRecordByTicketId(ticketId);
    }

    @Override
    public List<TicketRecordResponseDTO> getByDateRange(Long ticketId, LocalDate from, LocalDate to) {
        validateTicketEntityById(ticketId);

        if (from.isAfter(to)) {
            throw new IllegalArgumentException("'from' no puede ser posterior a 'to'.");
        }

        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.atTime(LocalTime.MAX);

        return ticketRecordDAO.findTicketRecordByTicketIdAndDateRange(ticketId, start, end);
    }
    
    private void validateTicketEntityById(Long ticketId) {
        ticketDAO.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException("Ticket no encontrado con id: " + ticketId));
    }
}

