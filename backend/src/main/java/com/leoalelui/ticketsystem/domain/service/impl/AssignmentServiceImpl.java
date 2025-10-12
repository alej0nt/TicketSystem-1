package com.leoalelui.ticketsystem.domain.service.impl;

import com.leoalelui.ticketsystem.domain.dto.request.AssignmentCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.request.NotificationCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.request.TicketUpdateStateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.AssignmentResponseDTO;
import com.leoalelui.ticketsystem.domain.dto.response.EmployeeResponseDTO;
import com.leoalelui.ticketsystem.domain.dto.response.TicketResponseDTO;
import com.leoalelui.ticketsystem.domain.exception.InvalidRoleException;
import com.leoalelui.ticketsystem.domain.exception.InvalidStateException;
import com.leoalelui.ticketsystem.domain.exception.ResourceNotFoundException;
import com.leoalelui.ticketsystem.domain.service.AssignmentService;
import com.leoalelui.ticketsystem.domain.service.EmployeeService;
import com.leoalelui.ticketsystem.domain.service.TicketService;
import com.leoalelui.ticketsystem.domain.service.NotificationService;
import com.leoalelui.ticketsystem.persistence.dao.AssignmentDAO;
import com.leoalelui.ticketsystem.persistence.enums.Role;
import com.leoalelui.ticketsystem.persistence.enums.State;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;

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
    private final NotificationService notificationService;

    @Override
    public AssignmentResponseDTO create(AssignmentCreateDTO assignmentCreateDTO) {
        Long ticketId = assignmentCreateDTO.getTicketId();
        Long employeeId = assignmentCreateDTO.getEmployeeId();

        TicketResponseDTO ticket = ticketService.getTicketById(ticketId);

        EmployeeResponseDTO employee = employeeService.getEmployeeById(employeeId);

        validateEmployeeAgent(employee);

        // Valida que el ticket esté en estado "Abierto"
        if (ticket.getState() != State.ABIERTO) {
            throw new InvalidStateException("Solo se pueden asignar tickets en estado 'Abierto'. Estado actual: " + ticket.getState());
        }

        ticketService.updateState(ticketId, new TicketUpdateStateDTO(State.EN_PROGRESO));
        sendNotificationToEmployees(ticket, employee);
        
        return assignmentDAO.save(assignmentCreateDTO);
    }

    @Override
    public List<AssignmentResponseDTO> getByEmployeeId(Long employeeId, String query) {
        validateEmployeeAgent(employeeService.getEmployeeById(employeeId));
        return assignmentDAO.getByEmployeeId(employeeId, query);
    }

    @Override
    public AssignmentResponseDTO getByTicketId(Long ticketId) {
        AssignmentResponseDTO assignment = assignmentDAO.getByTicketId(ticketId);

        if (assignment == null) {
            throw new ResourceNotFoundException("El ticket con id " + ticketId + " no tiene una asignación activa.");
        }

        return assignment;
    }

    @Override
    public AssignmentResponseDTO reassignEmployee(Long ticketId, Long newEmployeeId) {
        if (newEmployeeId == null || newEmployeeId == 0) {
            throw new DataIntegrityViolationException("Debes ingresar algun id del empleado agente");
        }
        AssignmentResponseDTO currentAssignment = getByTicketId(ticketId);

        // Validar ticket y estado usando DTOs
        TicketResponseDTO ticket = currentAssignment.getTicket();
//        if (ticket == null) {
//            throw new RuntimeException("El ticket asociado a la asignación no existe.");
//        }
        if (ticket.getState() == State.RESUELTO || ticket.getState() == State.CERRADO) {
            throw new InvalidStateException("No se puede reasignar un ticket en estado finalizado (" + ticket.getState() + ").");
        }

        // Validar nuevo empleado (DTO)
        EmployeeResponseDTO newEmployee = employeeService.getEmployeeById(newEmployeeId);
        validateEmployeeAgent(newEmployee);
        
        notificationService.create(new NotificationCreateDTO("Ya el ticket '" + ticket.getTitle() + "' acaba de ser reasignado a otro agente, ya no trabajas en el.", currentAssignment.getEmployee().getId()));
        sendNotificationToEmployees(ticket, newEmployee);

        return assignmentDAO.reassignByTicketId(ticketId, newEmployeeId);
    }

    private void validateEmployeeAgent(EmployeeResponseDTO employee) {
        if (employee.getRole() != Role.AGENT) {
            throw new InvalidRoleException("El empleado con id " + employee.getId() + " no tiene el rol de AGENTE.");
        }
    }
    
    private void sendNotificationToEmployees(TicketResponseDTO ticket, EmployeeResponseDTO agent) {
        notificationService.create(new NotificationCreateDTO("Acaban de asignar tu ticket '" + ticket.getTitle() + "' al agente '" + agent.getName() + "'", ticket.getEmployeeId()));
        notificationService.create(new NotificationCreateDTO("Acaban de asignarte el ticket '" + ticket.getTitle() + "' con prioridad " + ticket.getPriority(), agent.getId()));
    }

}
