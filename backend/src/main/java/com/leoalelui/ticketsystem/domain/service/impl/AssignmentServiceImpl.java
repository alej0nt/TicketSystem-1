package com.leoalelui.ticketsystem.domain.service.impl;

import com.leoalelui.ticketsystem.domain.dto.request.AssignmentCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.request.TicketUpdateStateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.AssignmentResponseDTO;
import com.leoalelui.ticketsystem.domain.dto.response.EmployeeResponseDTO;
import com.leoalelui.ticketsystem.domain.dto.response.TicketResponseDTO;
import com.leoalelui.ticketsystem.domain.service.AssignmentService;
import com.leoalelui.ticketsystem.domain.service.EmployeeService;
import com.leoalelui.ticketsystem.domain.service.TicketService;
import com.leoalelui.ticketsystem.persistence.dao.AssignmentDAO;
import com.leoalelui.ticketsystem.persistence.entity.AssignmentEntity;
import com.leoalelui.ticketsystem.persistence.entity.EmployeeEntity;
import com.leoalelui.ticketsystem.persistence.entity.TicketEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de asignaciones.
 *
 * @author Leonardo
 */
@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentDAO assignmentDAO;
    private final TicketService ticketService;
    private final EmployeeService employeeService;

    @Override
    public AssignmentResponseDTO create(AssignmentCreateDTO assignmentCreateDTO) {
        Long ticketId = assignmentCreateDTO.getTicketId();
        Long employeeId = assignmentCreateDTO.getEmployeeId();

        TicketResponseDTO ticket = ticketService.getTicketById(ticketId);

        EmployeeResponseDTO employee = employeeService.getEmployeeById(employeeId);

        validateEmployeeAgent(employee);

        // Valida que el ticket esté en estado "Abierto"
        String ticketState = ticket.getState() != null ? ticket.getState().toString() : null;
        if (ticketState == null || !ticketState.equalsIgnoreCase("Abierto")) {
            throw new RuntimeException("Solo se pueden asignar tickets en estado 'Abierto'. Estado actual: " + ticketState);
        }

        ticketService.updateState(ticketId, new TicketUpdateStateDTO("En progreso"));

        // Guarda la asignación
        return assignmentDAO.save(assignmentCreateDTO);
    }

    @Override
    public List<AssignmentResponseDTO> getByEmployeeId(Long employeeId) {
        return assignmentDAO.getByEmployeeId(employeeId);
    }

    @Override
    public AssignmentResponseDTO getByTicketId(Long ticketId) {
        AssignmentResponseDTO assignment = assignmentDAO.getByTicketId(ticketId);

        if (assignment == null) {
            throw new RuntimeException("El ticket con id " + ticketId + " no tiene una asignación activa.");
        }

        return assignment;
    }

    @Override
public AssignmentResponseDTO reassignEmployee(Long ticketId, Long newEmployeeId) {
    // Buscar asignación actual por ticket (entity)
    AssignmentEntity assignment = assignmentDAO.findEntityByTicketId(ticketId)
            .orElseThrow(() -> new RuntimeException("No existe una asignación para el ticket con id: " + ticketId));

    TicketEntity ticket = assignment.getTicket();
    if (ticket == null) {
        throw new RuntimeException("El ticket asociado a la asignación no existe.");
    }

    // Validar estado del ticket
    String ticketState = ticket.getState() != null ? ticket.getState().trim() : "";
    if (ticketState.equalsIgnoreCase("Resuelto") || ticketState.equalsIgnoreCase("Cerrado")) {
        throw new RuntimeException("No se puede reasignar un ticket en estado finalizado (" + ticketState + ").");
    }

    // Verificar nuevo empleado
    EmployeeResponseDTO newEmployee = employeeService.getEmployeeById(newEmployeeId);
    validateEmployeeAgent(newEmployee);

    // ESTO ME TOCA CAMBIARLO
    EmployeeEntity newEmployeeEntity = new EmployeeEntity();
    newEmployeeEntity.setId(newEmployee.getId());
    newEmployeeEntity.setName(newEmployee.getName());
    newEmployeeEntity.setRole(newEmployee.getRole());
    
    // Actualizar asignación
    assignment.setEmployee(newEmployeeEntity);
    return assignmentDAO.update(assignment);
}

    private void validateEmployeeAgent(EmployeeResponseDTO employee) {
        String role = employee.getRole() != null ? employee.getRole().toString() : null;
        if (role == null || !role.equalsIgnoreCase("AGENTE")) {
            throw new RuntimeException("El empleado con id " + employee.getId() + " no tiene el rol de AGENTE.");
        }
    }

}
