package com.leoalelui.ticketsystem.domain;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import com.leoalelui.ticketsystem.domain.dto.request.AssignmentCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.request.NotificationCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.request.TicketUpdateStateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.AssignmentResponseDTO;
import com.leoalelui.ticketsystem.domain.dto.response.CategoryResponseDTO;
import com.leoalelui.ticketsystem.domain.dto.response.EmployeeResponseDTO;
import com.leoalelui.ticketsystem.domain.dto.response.TicketResponseDTO;
import com.leoalelui.ticketsystem.domain.exception.InvalidRoleException;
import com.leoalelui.ticketsystem.domain.exception.ResourceNotFoundException;
import com.leoalelui.ticketsystem.domain.service.EmployeeService;
import com.leoalelui.ticketsystem.domain.service.NotificationService;
import com.leoalelui.ticketsystem.domain.service.TicketService;
import com.leoalelui.ticketsystem.domain.service.impl.AssignmentServiceImpl;
import com.leoalelui.ticketsystem.persistence.dao.AssignmentDAO;
import com.leoalelui.ticketsystem.persistence.enums.Priority;
import com.leoalelui.ticketsystem.persistence.enums.Role;
import com.leoalelui.ticketsystem.persistence.enums.State;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
@DisplayName("AssignmentService - Unit Tests ")
public class AssignmentServiceTest {
    @Mock
    private AssignmentDAO assignmentDAO;

    @Mock
    private TicketService ticketService;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private AssignmentServiceImpl assignmentService;

    // DATOS DE PRUEBA
    private AssignmentCreateDTO validAssignmentCreateDTO;
    private TicketResponseDTO validTicketResponse;
    private EmployeeResponseDTO validAgentResponse;
    private AssignmentResponseDTO expectedAssignmentResponse;
    private CategoryResponseDTO validCategory;
    private Long validTicketId;
    private Long validEmployeeId;

    @BeforeEach
    public void setUp() {
        validTicketId = 1L;
        validEmployeeId = 10L;

        validCategory = new CategoryResponseDTO(
                1L,
                "Soporte Técnico",
                "Problemas técnicos generales");

        // Ticket válido en estado ABIERTO
        validTicketResponse = new TicketResponseDTO(
                validTicketId,
                5L,
                validCategory,
                "Error en inicio de sesión",
                "El usuario no puede iniciar sesión correctamente",
                Priority.ALTA,
                State.ABIERTO, 
                LocalDateTime.now(),
                null);

        validAgentResponse = new EmployeeResponseDTO(
                validEmployeeId,
                "Juan Pérez",
                "juan.perez@empresa.com",
                Role.AGENT,
                "Soporte Técnico");

        validAssignmentCreateDTO = new AssignmentCreateDTO(
                validTicketId,
                validEmployeeId);

        // Respuesta esperada después de crear la asignación
        expectedAssignmentResponse = new AssignmentResponseDTO(
                1L,
                validTicketResponse,
                validAgentResponse,
                LocalDateTime.now());
    }
     // ==================== CREATE ASSIGNMENT TESTS ====================
    @Test
    @DisplayName("CREATE - Asignación válida debe crear asignación y cambiar estado del ticket a EN_PROGRESO")
    void createAssignment_ValidData_ShouldCreateAssignmentAndUpdateTicketState() {
        // ARRANGE Preparar el escenario
        when(ticketService.getTicketById(validTicketId))
                .thenReturn(validTicketResponse);

        when(employeeService.getEmployeeById(validEmployeeId))
                .thenReturn(validAgentResponse);

        when(assignmentDAO.save(any(AssignmentCreateDTO.class)))
                .thenReturn(expectedAssignmentResponse);

        // ACT Ejecutar el método bajo prueba
        AssignmentResponseDTO result = assignmentService.create(validAssignmentCreateDTO);

        // ASSERT Verificar los resultados
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTicket().getId()).isEqualTo(validTicketId);
        assertThat(result.getEmployee().getId()).isEqualTo(validEmployeeId);
        assertThat(result.getEmployee().getRole()).isEqualTo(Role.AGENT);

        // Verificar que se llamaron los métodos correctos en el orden esperado
        verify(ticketService, times(1)).getTicketById(validTicketId);
        verify(employeeService, times(1)).getEmployeeById(validEmployeeId);

        // Verificar que se actualizó el estado del ticket a EN_PROGRESO
        verify(ticketService, times(1)).updateState(
                eq(validTicketId),
                argThat(dto -> dto.getState() == State.EN_PROGRESO));
        verify(notificationService, times(2)).create(any(NotificationCreateDTO.class));

        verify(assignmentDAO, times(1)).save(any(AssignmentCreateDTO.class));
    }

    @Test
    @DisplayName("CREATE - TicketId null debe lanzar excepción al intentar obtener el ticket")
    void createAssignment_NullTicketId_ShouldThrowException() {
        AssignmentCreateDTO assignmentWithNullTicketId = new AssignmentCreateDTO(
                null, 
                validEmployeeId);

        when(ticketService.getTicketById(null))
                .thenThrow(new ResourceNotFoundException("Tiquete no encontrado"));

        assertThatThrownBy(() -> assignmentService.create(assignmentWithNullTicketId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Tiquete no encontrado");

        // Verificar que NO se llamaron los métodos posteriores
        verify(ticketService, times(1)).getTicketById(null);
        verify(employeeService, never()).getEmployeeById(anyLong());
        verify(ticketService, never()).updateState(anyLong(), any(TicketUpdateStateDTO.class));
        verify(notificationService, never()).create(any(NotificationCreateDTO.class));
        verify(assignmentDAO, never()).save(any(AssignmentCreateDTO.class));
    }
    // ==================== GET BY EMPLOYEE TESTS ====================

    @Test
    @DisplayName("GET BY EMPLOYEE - Debe retornar lista de asignaciones del agente")
    void getByEmployeeId_ValidAgent_ShouldReturnAssignments() {
        List<AssignmentResponseDTO> expectedList = Arrays.asList(
                new AssignmentResponseDTO(1L, validTicketResponse, validAgentResponse, LocalDateTime.now()),
                new AssignmentResponseDTO(2L, validTicketResponse, validAgentResponse, LocalDateTime.now())
        );

        when(employeeService.getEmployeeById(validEmployeeId)).thenReturn(validAgentResponse);
        when(assignmentDAO.getByEmployeeId(validEmployeeId)).thenReturn(expectedList);

        List<AssignmentResponseDTO> result = assignmentService.getByEmployeeId(validEmployeeId);

        assertThat(result).hasSize(2);
        assertThat(result).isEqualTo(expectedList);
        verify(employeeService, times(1)).getEmployeeById(validEmployeeId);
        verify(assignmentDAO, times(1)).getByEmployeeId(validEmployeeId);
    }

    @Test
    @DisplayName("GET BY EMPLOYEE - Empleado no AGENT debe lanzar InvalidRoleException")
    void getByEmployeeId_EmployeeNotAgent_ShouldThrowInvalidRoleException() {
        EmployeeResponseDTO userEmployee = new EmployeeResponseDTO(
                validEmployeeId, "User", "user@empresa.com", Role.USER, "Operaciones"
        );

        when(employeeService.getEmployeeById(validEmployeeId)).thenReturn(userEmployee);

        assertThatThrownBy(() -> assignmentService.getByEmployeeId(validEmployeeId))
                .isInstanceOf(InvalidRoleException.class)
                .hasMessageContaining("no tiene el rol de AGENTE");

        verify(assignmentDAO, never()).getByEmployeeId(anyLong());
    }

    // ==================== GET BY TICKET TESTS ====================

    @Test
    @DisplayName("GET BY TICKET - Debe retornar asignación del ticket")
    void getByTicketId_ExistingAssignment_ShouldReturnAssignment() {
        when(assignmentDAO.getByTicketId(validTicketId)).thenReturn(expectedAssignmentResponse);

        AssignmentResponseDTO result = assignmentService.getByTicketId(validTicketId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTicket().getId()).isEqualTo(validTicketId);
        verify(assignmentDAO, times(1)).getByTicketId(validTicketId);
    }

    @Test
    @DisplayName("GET BY TICKET - Ticket sin asignación debe lanzar ResourceNotFoundException")
    void getByTicketId_NoAssignment_ShouldThrowResourceNotFoundException() {
        when(assignmentDAO.getByTicketId(validTicketId)).thenReturn(null);

        assertThatThrownBy(() -> assignmentService.getByTicketId(validTicketId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("no tiene una asignación activa");

        verify(assignmentDAO, times(1)).getByTicketId(validTicketId);
    }

    // ==================== REASSIGN EMPLOYEE TESTS ====================

    @Test
    @DisplayName("REASSIGN - Reasignación válida debe actualizar agente")
    void reassignEmployee_ValidData_ShouldReassign() {
        Long newEmployeeId = 20L;
        EmployeeResponseDTO newAgent = new EmployeeResponseDTO(
                newEmployeeId, "María López", "maria@empresa.com", Role.AGENT, "Soporte"
        );
        AssignmentResponseDTO updatedAssignment = new AssignmentResponseDTO(
                1L, validTicketResponse, newAgent, LocalDateTime.now()
        );

        when(assignmentDAO.getByTicketId(validTicketId)).thenReturn(expectedAssignmentResponse);
        when(employeeService.getEmployeeById(newEmployeeId)).thenReturn(newAgent);
        when(assignmentDAO.reassignByTicketId(validTicketId, newEmployeeId)).thenReturn(updatedAssignment);

        AssignmentResponseDTO result = assignmentService.reassignEmployee(validTicketId, newEmployeeId);

        assertThat(result).isNotNull();
        assertThat(result.getEmployee().getId()).isEqualTo(newEmployeeId);
        verify(assignmentDAO, times(1)).getByTicketId(validTicketId);
        verify(employeeService, times(1)).getEmployeeById(newEmployeeId);
        verify(notificationService, times(3)).create(any(NotificationCreateDTO.class));
        verify(assignmentDAO, times(1)).reassignByTicketId(validTicketId, newEmployeeId);
    }

    @Test
    @DisplayName("REASSIGN - NewEmployeeId null debe lanzar DataIntegrityViolationException")
    void reassignEmployee_NullEmployeeId_ShouldThrowDataIntegrityViolationException() {
        assertThatThrownBy(() -> assignmentService.reassignEmployee(validTicketId, null))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("Debes ingresar algun id del empleado agente");

        verify(assignmentDAO, never()).reassignByTicketId(anyLong(), anyLong());
    }

    @Test
    @DisplayName("REASSIGN - Nuevo empleado no AGENT debe lanzar InvalidRoleException")
    void reassignEmployee_NewEmployeeNotAgent_ShouldThrowInvalidRoleException() {
        Long newEmployeeId = 20L;
        EmployeeResponseDTO adminEmployee = new EmployeeResponseDTO(
                newEmployeeId, "Admin", "admin@empresa.com", Role.ADMIN, "Administración"
        );

        when(assignmentDAO.getByTicketId(validTicketId)).thenReturn(expectedAssignmentResponse);
        when(employeeService.getEmployeeById(newEmployeeId)).thenReturn(adminEmployee);

        assertThatThrownBy(() -> assignmentService.reassignEmployee(validTicketId, newEmployeeId))
                .isInstanceOf(InvalidRoleException.class)
                .hasMessageContaining("no tiene el rol de AGENTE");

        verify(assignmentDAO, never()).reassignByTicketId(anyLong(), anyLong());
    }
}
