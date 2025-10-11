package com.leoalelui.ticketsystem.domain.service.impl;

import com.leoalelui.ticketsystem.domain.dto.request.CommentCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.CommentResponseDTO;
import com.leoalelui.ticketsystem.domain.dto.response.EmployeeResponseDTO;
import com.leoalelui.ticketsystem.domain.dto.response.TicketResponseDTO;
import com.leoalelui.ticketsystem.domain.exception.ResourceNotFoundException;
import com.leoalelui.ticketsystem.domain.service.CommentService;
import com.leoalelui.ticketsystem.persistence.dao.CommentDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author alej0nt
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentDAO commentDAO;
    private final TicketServiceImpl ticketService;
    private final EmployeeServiceImpl employeeService;

    @Override
    public CommentResponseDTO save(CommentCreateDTO commentCreateDTO) {
        EmployeeResponseDTO employee = employeeService.getEmployeeById(commentCreateDTO.getEmployeeId());
        TicketResponseDTO ticket = ticketService.getTicketById(commentCreateDTO.getTicketId());
        return commentDAO.save(commentCreateDTO);
    }

    @Override
    public void delete (long id) {
        CommentResponseDTO comment = commentDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se encontro el comentario con el id: " + id));
        commentDAO.delete(id);
    }

    @Override
    public List<CommentResponseDTO> findAllByTicketId(long id) {
        TicketResponseDTO ticket = ticketService.getTicketById(id);
        return commentDAO.findAllByTicketId(id);
    }
}
