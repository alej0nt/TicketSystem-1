package com.leoalelui.ticketsystem.domain.service.impl;

import com.leoalelui.ticketsystem.domain.dto.request.AssignmentCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.AssignmentResponseDTO;
import com.leoalelui.ticketsystem.domain.service.AssignmentService;
import com.leoalelui.ticketsystem.persistence.dao.AssignmentDAO;
import com.leoalelui.ticketsystem.persistence.dao.EmployeeDAO;
import com.leoalelui.ticketsystem.persistence.dao.TicketDAO;
import com.leoalelui.ticketsystem.persistence.entity.AssignmentEntity;
import com.leoalelui.ticketsystem.persistence.entity.EmployeeEntity;
import com.leoalelui.ticketsystem.persistence.entity.TicketEntity;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio de asignaciones.
 * @author Leonardo Argoty
 */
@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentDAO assignmentDAO;
    private final TicketDAO ticketDAO;
    private final EmployeeDAO employeeDAO;

    @Override
    public AssignmentResponseDTO create(AssignmentCreateDTO assignmentCreateDTO) {
        Long ticketId = assignmentCreateDTO.getTicketId();
        Long employeeId = assignmentCreateDTO.getEmployeeId();

        // Verifica existencia del ticket
        TicketEntity ticket = ticketDAO.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado con id: " + ticketId));

        // Verifica existencia del empleado
        EmployeeEntity employee = employeeDAO.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + employeeId));

        // Valida rol del empleado (debe ser agente)
        String role = employee.getRole() != null ? employee.getRole().toString() : null;
        if (role == null || !(role.equalsIgnoreCase("AGENTE"))) {
            throw new RuntimeException("El empleado con id " + employeeId + " no tiene el rol de AGENTE.");
        }

        // Valida que el ticket no esté cerrado/resuelto
        String ticketState = ticket.getState() != null ? ticket.getState().toString() : null;
        if (ticketState != null) {
            String s = ticketState.trim().toLowerCase();
            if (s.equals("resuelto") || s.equals("cerrado")) {
                throw new RuntimeException("No se puede asignar un ticket en estado finalizado (" + ticketState + ").");
            }
        }

        return assignmentDAO.save(assignmentCreateDTO);
    }

    @Override
    public List<AssignmentResponseDTO> getByEmployeeId(Long employeeId) {
        return assignmentDAO.getByEmployeeId(employeeId);
    }

    @Override
    public AssignmentResponseDTO getByTicketId(Long ticketId) {
        return assignmentDAO.getByTicketId(ticketId);
    }

    @Override
    public AssignmentResponseDTO reassignEmployee(Long assignmentId, Long newEmployeeId) {
        // Buscar la asignación actual
        Optional<AssignmentEntity> optionalAssignment = assignmentDAO.findById(assignmentId);
        if (optionalAssignment.isEmpty()) {
            throw new RuntimeException("Asignación no encontrada con id: " + assignmentId);
        }
        AssignmentEntity assignment = optionalAssignment.get();

        // Verificar ticket asociado
        TicketEntity ticket = assignment.getTicket();
        if (ticket == null) {
            throw new RuntimeException("El ticket asociado a la asignación no existe.");
        }

        // Validar estado del ticket
        String ticketState = ticket.getState() != null ? ticket.getState().toLowerCase() : null;
        if ("resuelto".equals(ticketState) || "cerrado".equals(ticketState)) {
            throw new RuntimeException("No se puede reasignar un ticket en estado finalizado (" + ticketState + ").");
        }

        // Verificar nuevo empleado
        EmployeeEntity newEmployee = employeeDAO.findById(newEmployeeId)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + newEmployeeId));

        // Validar rol del nuevo empleado
        String role = newEmployee.getRole() != null ? newEmployee.getRole().toString() : null;
        if (role == null || !(role.equalsIgnoreCase("AGENTE"))) {
            throw new RuntimeException("El nuevo empleado con id " + newEmployeeId + " no tiene el rol de AGENTE.");
        }

        // Actualizar asignación
        assignment.setEmployee(newEmployee);
        return assignmentDAO.update(assignment);
    }
}
