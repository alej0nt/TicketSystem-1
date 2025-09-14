package com.leoalelui.ticketsystem.domain.service;

import com.leoalelui.ticketsystem.domain.dto.request.AssignmentCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.AssignmentResponseDTO;
import java.util.List;

/**
 * @author Leonardo Argoty
 */
public interface AssignmentService {

    AssignmentResponseDTO create(AssignmentCreateDTO assignmentCreateDTO);

    List<AssignmentResponseDTO> getByEmployeeId(Long employeeId);

    AssignmentResponseDTO getByTicketId(Long ticketId);

    AssignmentResponseDTO reassignEmployee(Long assignmentId, Long newEmployeeId);
 }
