package com.leoalelui.ticketsystem.domain.service.impl;

import com.leoalelui.ticketsystem.domain.dto.request.TicketRecordCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.TicketRecordResponseDTO;
import com.leoalelui.ticketsystem.domain.service.TicketRecordService;
import com.leoalelui.ticketsystem.persistence.dao.TicketRecordDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Servicio para TicketRecord
 */
@RequiredArgsConstructor
@Service
public class TicketRecordServiceImpl implements TicketRecordService {

    private final TicketRecordDAO ticketRecordDAO;
    private final TicketServiceImpl ticketService;

    @Override
    public TicketRecordResponseDTO create(TicketRecordCreateDTO ticketRecordCreateDTO) {
        Long ticketId = ticketRecordCreateDTO.getTicketId();

        ticketService.getTicketById(ticketId);

        String previousState = ticketRecordCreateDTO.getPreviousState();
        String nextState = ticketRecordCreateDTO.getNextState();

//        if (previousState == null || nextState == null) {
//            throw new IllegalArgumentException("previousState y nextState son requeridos.");
//        }
        if (previousState.equalsIgnoreCase(nextState)) {
            throw new IllegalArgumentException("El ticket no puede cambiar al mismo estado que el anterior.");
        }

        // Crear el registro (DAO hace la persistencia)
        return ticketRecordDAO.create(ticketRecordCreateDTO);
    }

    @Override
    public List<TicketRecordResponseDTO> getByTicketId(Long ticketId) {
        ticketService.getTicketById(ticketId);
        return ticketRecordDAO.findTicketRecordByTicketId(ticketId);
    }

    @Override
    public List<TicketRecordResponseDTO> getByDateRange(Long ticketId, LocalDate from, LocalDate to) {
        ticketService.getTicketById(ticketId);

//        if (from == null || to == null) {
//            throw new IllegalArgumentException("Las fechas 'from' y 'to' son requeridas.");
//        }
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("'from' no puede ser posterior a 'to'.");
        }

        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.atTime(LocalTime.MAX);

        // Petición directa al DAO que ya hace la consulta filtrada en BD
        return ticketRecordDAO.findTicketRecordByTicketIdAndDateRange(ticketId, start, end);
    }
}
