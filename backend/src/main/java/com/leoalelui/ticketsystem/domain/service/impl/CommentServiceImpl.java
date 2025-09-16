package com.leoalelui.ticketsystem.domain.service.impl;

import com.leoalelui.ticketsystem.domain.dto.request.CommentCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.CommentResponseDTO;
import com.leoalelui.ticketsystem.domain.dto.response.EmployeeResponseDTO;
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
    private final EmployeeServiceImpl employeeService;

    @Override
    public CommentResponseDTO save(CommentCreateDTO commentCreateDTO) {
        validateEmployeeComment(commentCreateDTO);
        return commentDAO.save(commentCreateDTO);
    }

    @Override
    public boolean delete (long id) {
        return commentDAO.delete(id);
    }

    @Override
    public List<CommentResponseDTO> findAllByTicketId(long id) {
        return commentDAO.findAllByTicketId(id);
    }


    /// private methods to validate
    private void validateEmployeeComment(CommentCreateDTO commentCreateDTO) {
        EmployeeResponseDTO employee = employeeService.getEmployeeById(commentCreateDTO.getEmployeeId());
    }
}
