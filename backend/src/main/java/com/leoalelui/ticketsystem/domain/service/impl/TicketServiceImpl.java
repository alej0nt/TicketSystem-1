package com.leoalelui.ticketsystem.domain.service.impl;

import com.leoalelui.ticketsystem.domain.dto.request.TicketCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.request.TicketUpdateStateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.CommentResponseDTO;
import com.leoalelui.ticketsystem.domain.dto.response.TicketRecordResponseDTO;
import com.leoalelui.ticketsystem.domain.dto.response.TicketResponseDTO;
import com.leoalelui.ticketsystem.domain.service.TicketService;
import com.leoalelui.ticketsystem.persistence.dao.*;
import com.leoalelui.ticketsystem.persistence.entity.TicketEntity;
import jakarta.persistence.EntityNotFoundException;
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
    private final TicketRecordDAO ticketRecordDAO;

    @Override
    public TicketResponseDTO createTicket(TicketCreateDTO createDTO) {
        boolean existsEmployee = employeeDAO.existsById(createDTO.getEmployeeId());
        if (!existsEmployee) throw new EntityNotFoundException("Empleado no encontrado.");

        boolean existsCategory = categoryDAO.existsById(createDTO.getCategoryId());
        if (!existsCategory) throw new EntityNotFoundException("Categoria no encontrada.");

        return ticketDAO.save(createDTO);
    }

    @Override
    public TicketResponseDTO updateState(Long id, TicketUpdateStateDTO updateStateDTO) {
        return ticketDAO.updateState(id, updateStateDTO)
                .orElseThrow(() -> new EntityNotFoundException("Tiquete no encontrado."));
    }

    @Override
    public void deleteTicket(Long id) {
        boolean existId = ticketDAO.existsById(id);
        if (!existId) throw new EntityNotFoundException("Tiquete no encontrado.");
        ticketDAO.deleteById(id);
    }

    @Override
    public TicketResponseDTO getTicketById(Long id) {
        return ticketDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tiquete no encontrado"));
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
    public List<CommentResponseDTO> getAllCommentsByTicketId(Long id) {
        return commentDAO.findAllByTicketId(id);
    }

    @Override
    public List<TicketRecordResponseDTO> getAllTicketRecordsByTicketId(Long id) {
        return ticketRecordDAO.findTicketRecordByTicketId(id);
    }
}
