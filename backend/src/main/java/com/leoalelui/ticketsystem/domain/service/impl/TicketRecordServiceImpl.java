package com.leoalelui.ticketsystem.domain.service.impl;

import com.leoalelui.ticketsystem.domain.dto.request.TicketRecordCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.TicketRecordResponseDTO;
import com.leoalelui.ticketsystem.domain.exception.InvalidStateException;
import com.leoalelui.ticketsystem.domain.service.TicketRecordService;
import com.leoalelui.ticketsystem.persistence.dao.TicketRecordDAO;
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

    @Override
    public TicketRecordResponseDTO create(TicketRecordCreateDTO ticketRecordCreateDTO) {
        // Validar cambio de estado distinto
        if (ticketRecordCreateDTO.getPreviousState().equals(ticketRecordCreateDTO.getNextState())) {
            throw new InvalidStateException("El ticket no puede cambiar al mismo estado que el anterior.");
        }

        return ticketRecordDAO.create(ticketRecordCreateDTO);
    }

    @Override
    public List<TicketRecordResponseDTO> getByTicketId(Long ticketId) {

        return ticketRecordDAO.findTicketRecordByTicketId(ticketId);
    }

    @Override
    public List<TicketRecordResponseDTO> getByDateRange(Long ticketId, LocalDate from, LocalDate to) {
        if (from == null && to == null) {
            return getByTicketId(ticketId);
        }
    
        if (to == null) {
            to = LocalDate.now();
        }
        
        if (from== null) {
            from = LocalDate.of(2000, 1, 1); 
        }
        
        if (from.isAfter(to)) {
            throw new InvalidStateException("'from' no puede ser posterior a 'to'.");
        }

        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.atTime(LocalTime.MAX);

        return ticketRecordDAO.findTicketRecordByTicketIdAndDateRange(ticketId, start, end);
    }
}