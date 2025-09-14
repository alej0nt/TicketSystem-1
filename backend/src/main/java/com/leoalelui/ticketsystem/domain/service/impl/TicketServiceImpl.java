package com.leoalelui.ticketsystem.domain.service.impl;

import com.leoalelui.ticketsystem.domain.dto.request.TicketCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.request.TicketUpdateStateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.TicketResponseDTO;
import com.leoalelui.ticketsystem.domain.service.TicketService;
import com.leoalelui.ticketsystem.persistence.dao.TicketDAO;
import com.leoalelui.ticketsystem.persistence.entity.TicketEntity;
import com.leoalelui.ticketsystem.persistence.mapper.TicketMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketDAO ticketDAO;
    private final TicketMapper ticketMapper;

    @Override
    public TicketResponseDTO createTicket(TicketCreateDTO ticketCreateDTO) {
        TicketEntity ticket = ticketMapper.toEntity(ticketCreateDTO);

        ticket.setState("ABIERTO");
        ticket.setCreationDate(LocalDateTime.now());

        TicketEntity saved = ticketDAO.save(ticket);
        return ticketMapper.toResponseDTO(saved);
    }

    @Override
    public TicketResponseDTO updateState(Long id, TicketUpdateStateDTO ticketUpdateStateDTO) {
        TicketEntity ticket = ticketDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tiquete no encontrado"));

        ticket.setState(ticketUpdateStateDTO.getState());

        TicketEntity updated = ticketDAO.save(ticket);
        return ticketMapper.toResponseDTO(updated);
    }

    @Override
    public void deleteTicket(Long id) {
        ticketDAO.deleteById(id);
    }

    @Override
    public TicketResponseDTO getTicketById(Long id) {
        TicketEntity ticket = ticketDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tiquete no encontrado"));
        return ticketMapper.toResponseDTO(ticket);
    }

    @Override
    public List<TicketResponseDTO> getAllTickets() {
        return ticketDAO.findAll()
                .stream()
                .map(ticketMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<TicketResponseDTO> getTicketsByState(String state) {
        return ticketDAO.findByState(state)
                .stream()
                .map(ticketMapper::toResponseDTO)
                .toList();
    }
}
