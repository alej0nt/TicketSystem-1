package com.leoalelui.ticketsystem.domain;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.leoalelui.ticketsystem.domain.dto.request.NotificationCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.request.TicketCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.request.TicketRecordCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.request.TicketUpdateStateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.CategoryResponseDTO;
import com.leoalelui.ticketsystem.domain.dto.response.CommentResponseDTO;
import com.leoalelui.ticketsystem.domain.dto.response.EmployeeResponseDTO;
import com.leoalelui.ticketsystem.domain.dto.response.TicketRecordResponseDTO;
import com.leoalelui.ticketsystem.domain.dto.response.TicketResponseDTO;
import com.leoalelui.ticketsystem.domain.exception.InvalidStateException;
import com.leoalelui.ticketsystem.domain.exception.ResourceNotFoundException;
import com.leoalelui.ticketsystem.domain.service.NotificationService;
import com.leoalelui.ticketsystem.domain.service.TicketRecordService;
import com.leoalelui.ticketsystem.domain.service.impl.TicketServiceImpl;
import com.leoalelui.ticketsystem.persistence.dao.CategoryDAO;
import com.leoalelui.ticketsystem.persistence.dao.CommentDAO;
import com.leoalelui.ticketsystem.persistence.dao.EmployeeDAO;
import com.leoalelui.ticketsystem.persistence.dao.TicketDAO;
import com.leoalelui.ticketsystem.persistence.enums.Priority;
import com.leoalelui.ticketsystem.persistence.enums.Role;
import com.leoalelui.ticketsystem.persistence.enums.State;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
@DisplayName("TicketService - Unit Tests")
public class TicketServiceTest {

    @Mock
    private TicketDAO ticketDAO;

    @Mock
    private EmployeeDAO employeeDAO;

    @Mock
    private CategoryDAO categoryDAO;

    @Mock
    private CommentDAO commentDAO;

    @Mock
    private TicketRecordService ticketRecordService;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private TicketServiceImpl ticketService;

    // DATOS DE PRUEBA
    private TicketCreateDTO validTicketCreateDTO;
    private TicketResponseDTO validTicketResponse;
    private CategoryResponseDTO validCategory;
    private EmployeeResponseDTO validEmployee;
    private Long validTicketId;
    private Long validEmployeeId;
    private Long validCategoryId;

    @BeforeEach
    public void setUp() {
        validTicketId = 1L;
        validEmployeeId = 5L;
        validCategoryId = 10L;

        validCategory = new CategoryResponseDTO(
                validCategoryId,
                "Soporte Técnico",
                "Problemas técnicos generales");

        validEmployee = new EmployeeResponseDTO(
                validEmployeeId,
                "Juan Pérez",
                "juan.perez@empresa.com",
                Role.USER,
                "Operaciones");

        validTicketCreateDTO = new TicketCreateDTO(
                validEmployeeId,
                "Error en inicio de sesión",
                "El usuario no puede iniciar sesión correctamente",
                validCategoryId,
                Priority.ALTA);

        validTicketResponse = new TicketResponseDTO(
                validTicketId,
                validEmployeeId,
                validCategory,
                "Error en inicio de sesión",
                "El usuario no puede iniciar sesión correctamente",
                Priority.ALTA,
                State.ABIERTO,
                LocalDateTime.now(),
                null);
    }

    // ==================== CREATE TICKET TESTS ====================

    @Test
    @DisplayName("CREATE - Ticket válido debe crear ticket correctamente")
    void createTicket_ValidData_ShouldCreateTicket() {
        // ARRANGE
        when(employeeDAO.existsById(validEmployeeId)).thenReturn(true);
        when(categoryDAO.existsById(validCategoryId)).thenReturn(true);
        when(ticketDAO.save(validTicketCreateDTO)).thenReturn(validTicketResponse);

        // ACT
        TicketResponseDTO result = ticketService.createTicket(validTicketCreateDTO);

        // ASSERT
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(validTicketId);
        assertThat(result.getTitle()).isEqualTo("Error en inicio de sesión");
        assertThat(result.getState()).isEqualTo(State.ABIERTO);
        assertThat(result.getPriority()).isEqualTo(Priority.ALTA);

        verify(employeeDAO, times(1)).existsById(validEmployeeId);
        verify(categoryDAO, times(1)).existsById(validCategoryId);
        verify(ticketDAO, times(1)).save(validTicketCreateDTO);
    }

    @Test
    @DisplayName("CREATE - EmployeeId no existe debe lanzar ResourceNotFoundException")
    void createTicket_NonExistentEmployee_ShouldThrowException() {
        // ARRANGE
        when(employeeDAO.existsById(validEmployeeId)).thenReturn(false);

        // ACT & ASSERT
        assertThatThrownBy(() -> ticketService.createTicket(validTicketCreateDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Empleado no encontrado con ID");

        verify(employeeDAO, times(1)).existsById(validEmployeeId);
        verify(categoryDAO, never()).existsById(anyLong());
        verify(ticketDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - CategoryId no existe debe lanzar ResourceNotFoundException")
    void createTicket_NonExistentCategory_ShouldThrowException() {
        // ARRANGE
        when(employeeDAO.existsById(validEmployeeId)).thenReturn(true);
        when(categoryDAO.existsById(validCategoryId)).thenReturn(false);

        // ACT & ASSERT
        assertThatThrownBy(() -> ticketService.createTicket(validTicketCreateDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Categoría no encontrada con ID");

        verify(employeeDAO, times(1)).existsById(validEmployeeId);
        verify(categoryDAO, times(1)).existsById(validCategoryId);
        verify(ticketDAO, never()).save(any());
    }

    // ==================== UPDATE STATE TESTS ====================

    @Test
    @DisplayName("UPDATE STATE - Transición válida ABIERTO -> EN_PROGRESO debe actualizar estado")
    void updateState_ValidTransitionAbiertoToEnProgreso_ShouldUpdateState() {
        // ARRANGE
        TicketUpdateStateDTO updateDTO = new TicketUpdateStateDTO(State.EN_PROGRESO);
        TicketResponseDTO updatedTicket = new TicketResponseDTO(
                validTicketId,
                validEmployeeId,
                validCategory,
                "Error en inicio de sesión",
                "El usuario no puede iniciar sesión correctamente",
                Priority.ALTA,
                State.EN_PROGRESO,
                LocalDateTime.now(),
                null);

        when(ticketDAO.findById(validTicketId)).thenReturn(Optional.of(validTicketResponse));
        when(ticketDAO.updateState(validTicketId, updateDTO)).thenReturn(Optional.of(updatedTicket));

        // ACT
        TicketResponseDTO result = ticketService.updateState(validTicketId, updateDTO);

        // ASSERT
        assertThat(result).isNotNull();
        assertThat(result.getState()).isEqualTo(State.EN_PROGRESO);

        verify(ticketDAO, times(1)).findById(validTicketId);
        verify(ticketRecordService, times(1)).create(any(TicketRecordCreateDTO.class));
        verify(ticketDAO, times(1)).updateState(validTicketId, updateDTO);
        verify(notificationService, times(1)).create(any(NotificationCreateDTO.class));
    }

    @Test
    @DisplayName("UPDATE STATE - Transición válida EN_PROGRESO -> RESUELTO debe actualizar estado")
    void updateState_ValidTransitionEnProgresoToResuelto_ShouldUpdateState() {
        // ARRANGE
        TicketResponseDTO ticketEnProgreso = new TicketResponseDTO(
                validTicketId,
                validEmployeeId,
                validCategory,
                "Error en inicio de sesión",
                "Descripción",
                Priority.ALTA,
                State.EN_PROGRESO,
                LocalDateTime.now(),
                null);

        TicketUpdateStateDTO updateDTO = new TicketUpdateStateDTO(State.RESUELTO);
        TicketResponseDTO updatedTicket = new TicketResponseDTO(
                validTicketId,
                validEmployeeId,
                validCategory,
                "Error en inicio de sesión",
                "Descripción",
                Priority.ALTA,
                State.RESUELTO,
                LocalDateTime.now(),
                LocalDateTime.now());

        when(ticketDAO.findById(validTicketId)).thenReturn(Optional.of(ticketEnProgreso));
        when(ticketDAO.updateState(validTicketId, updateDTO)).thenReturn(Optional.of(updatedTicket));

        // ACT
        TicketResponseDTO result = ticketService.updateState(validTicketId, updateDTO);

        // ASSERT
        assertThat(result).isNotNull();
        assertThat(result.getState()).isEqualTo(State.RESUELTO);

        verify(ticketRecordService, times(1)).create(any(TicketRecordCreateDTO.class));
        verify(notificationService, times(1)).create(any(NotificationCreateDTO.class));
    }

    @Test
    @DisplayName("UPDATE STATE - Mismo estado debe lanzar InvalidStateException")
    void updateState_SameState_ShouldThrowException() {
        // ARRANGE
        TicketUpdateStateDTO updateDTO = new TicketUpdateStateDTO(State.ABIERTO);

        when(ticketDAO.findById(validTicketId)).thenReturn(Optional.of(validTicketResponse));

        // ACT & ASSERT
        assertThatThrownBy(() -> ticketService.updateState(validTicketId, updateDTO))
                .isInstanceOf(InvalidStateException.class)
                .hasMessageContaining("El tiquete ya está en el estado");

        verify(ticketRecordService, never()).create(any());
        verify(ticketDAO, never()).updateState(anyLong(), any());
        verify(notificationService, never()).create(any());
    }

    @Test
    @DisplayName("UPDATE STATE - Transición inválida ABIERTO -> RESUELTO debe lanzar InvalidStateException")
    void updateState_InvalidTransitionAbiertoToResuelto_ShouldThrowException() {
        // ARRANGE
        TicketUpdateStateDTO updateDTO = new TicketUpdateStateDTO(State.RESUELTO);

        when(ticketDAO.findById(validTicketId)).thenReturn(Optional.of(validTicketResponse));

        // ACT & ASSERT
        assertThatThrownBy(() -> ticketService.updateState(validTicketId, updateDTO))
                .isInstanceOf(InvalidStateException.class)
                .hasMessageContaining("solo se puede cambiar a 'En progreso'");

        verify(ticketRecordService, never()).create(any());
        verify(ticketDAO, never()).updateState(anyLong(), any());
    }

    @Test
    @DisplayName("UPDATE STATE - Transición inválida EN_PROGRESO -> CERRADO debe lanzar InvalidStateException")
    void updateState_InvalidTransitionEnProgresoToCerrado_ShouldThrowException() {
        // ARRANGE
        TicketResponseDTO ticketEnProgreso = new TicketResponseDTO(
                validTicketId,
                validEmployeeId,
                validCategory,
                "Ticket",
                "Descripción",
                Priority.ALTA,
                State.EN_PROGRESO,
                LocalDateTime.now(),
                null);

        TicketUpdateStateDTO updateDTO = new TicketUpdateStateDTO(State.CERRADO);

        when(ticketDAO.findById(validTicketId)).thenReturn(Optional.of(ticketEnProgreso));

        // ACT & ASSERT
        assertThatThrownBy(() -> ticketService.updateState(validTicketId, updateDTO))
                .isInstanceOf(InvalidStateException.class)
                .hasMessageContaining("solo se puede cambiar a 'Resuelto' o 'Abierto'");

        verify(ticketRecordService, never()).create(any());
        verify(ticketDAO, never()).updateState(anyLong(), any());
    }

    @Test
    @DisplayName("UPDATE STATE - Transición válida RESUELTO -> CERRADO debe actualizar estado")
    void updateState_ValidTransitionResueltoToCerrado_ShouldUpdateState() {
        // ARRANGE
        TicketResponseDTO ticketResuelto = new TicketResponseDTO(
                validTicketId,
                validEmployeeId,
                validCategory,
                "Ticket",
                "Descripción",
                Priority.ALTA,
                State.RESUELTO,
                LocalDateTime.now(),
                LocalDateTime.now());

        TicketUpdateStateDTO updateDTO = new TicketUpdateStateDTO(State.CERRADO);
        TicketResponseDTO updatedTicket = new TicketResponseDTO(
                validTicketId,
                validEmployeeId,
                validCategory,
                "Ticket",
                "Descripción",
                Priority.ALTA,
                State.CERRADO,
                LocalDateTime.now(),
                LocalDateTime.now());

        when(ticketDAO.findById(validTicketId)).thenReturn(Optional.of(ticketResuelto));
        when(ticketDAO.updateState(validTicketId, updateDTO)).thenReturn(Optional.of(updatedTicket));

        // ACT
        TicketResponseDTO result = ticketService.updateState(validTicketId, updateDTO);

        // ASSERT
        assertThat(result).isNotNull();
        assertThat(result.getState()).isEqualTo(State.CERRADO);

        verify(ticketRecordService, times(1)).create(any(TicketRecordCreateDTO.class));
        verify(ticketDAO, times(1)).updateState(validTicketId, updateDTO);
        verify(notificationService, times(1)).create(any(NotificationCreateDTO.class));
    }

    @Test
    @DisplayName("UPDATE STATE - Transición válida CERRADO -> RESUELTO debe actualizar estado")
    void updateState_ValidTransitionCerradoToResuelto_ShouldUpdateState() {
        // ARRANGE
        TicketResponseDTO ticketCerrado = new TicketResponseDTO(
                validTicketId,
                validEmployeeId,
                validCategory,
                "Ticket",
                "Descripción",
                Priority.ALTA,
                State.CERRADO,
                LocalDateTime.now(),
                LocalDateTime.now());

        TicketUpdateStateDTO updateDTO = new TicketUpdateStateDTO(State.RESUELTO);
        TicketResponseDTO updatedTicket = new TicketResponseDTO(
                validTicketId,
                validEmployeeId,
                validCategory,
                "Ticket",
                "Descripción",
                Priority.ALTA,
                State.RESUELTO,
                LocalDateTime.now(),
                LocalDateTime.now());

        when(ticketDAO.findById(validTicketId)).thenReturn(Optional.of(ticketCerrado));
        when(ticketDAO.updateState(validTicketId, updateDTO)).thenReturn(Optional.of(updatedTicket));

        // ACT
        TicketResponseDTO result = ticketService.updateState(validTicketId, updateDTO);

        // ASSERT
        assertThat(result).isNotNull();
        assertThat(result.getState()).isEqualTo(State.RESUELTO);

        verify(ticketRecordService, times(1)).create(any(TicketRecordCreateDTO.class));
        verify(ticketDAO, times(1)).updateState(validTicketId, updateDTO);
        verify(notificationService, times(1)).create(any(NotificationCreateDTO.class));
    }

    @Test
    @DisplayName("UPDATE STATE - Transición inválida CERRADO -> ABIERTO debe lanzar InvalidStateException")
    void updateState_InvalidTransitionCerradoToAbierto_ShouldThrowException() {
        // ARRANGE
        TicketResponseDTO ticketCerrado = new TicketResponseDTO(
                validTicketId,
                validEmployeeId,
                validCategory,
                "Ticket",
                "Descripción",
                Priority.ALTA,
                State.CERRADO,
                LocalDateTime.now(),
                LocalDateTime.now());

        TicketUpdateStateDTO updateDTO = new TicketUpdateStateDTO(State.ABIERTO);

        when(ticketDAO.findById(validTicketId)).thenReturn(Optional.of(ticketCerrado));

        // ACT & ASSERT
        assertThatThrownBy(() -> ticketService.updateState(validTicketId, updateDTO))
                .isInstanceOf(InvalidStateException.class)
                .hasMessageContaining("solo se puede reabrir a 'Resuelto'");

        verify(ticketRecordService, never()).create(any());
        verify(ticketDAO, never()).updateState(anyLong(), any());
    }

    @Test
    @DisplayName("UPDATE STATE - Ticket no existe debe lanzar ResourceNotFoundException")
    void updateState_NonExistentTicket_ShouldThrowException() {
        // ARRANGE
        TicketUpdateStateDTO updateDTO = new TicketUpdateStateDTO(State.EN_PROGRESO);
        when(ticketDAO.findById(validTicketId)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> ticketService.updateState(validTicketId, updateDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Tiquete no encontrado con ID");

        verify(ticketDAO, times(1)).findById(validTicketId);
        verify(ticketDAO, never()).updateState(anyLong(), any());
    }

    @Test
    @DisplayName("UPDATE STATE - Error al actualizar debe lanzar ResourceNotFoundException")
    void updateState_UpdateFails_ShouldThrowException() {
        // ARRANGE
        TicketUpdateStateDTO updateDTO = new TicketUpdateStateDTO(State.EN_PROGRESO);

        when(ticketDAO.findById(validTicketId)).thenReturn(Optional.of(validTicketResponse));
        when(ticketDAO.updateState(validTicketId, updateDTO)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> ticketService.updateState(validTicketId, updateDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Error al actualizar ticket con ID");

        verify(ticketRecordService, times(1)).create(any(TicketRecordCreateDTO.class));
        verify(ticketDAO, times(1)).updateState(validTicketId, updateDTO);
        verify(notificationService, never()).create(any());
    }

    @Test
    @DisplayName("UPDATE STATE - Transición inválida RESUELTO -> ABIERTO debe lanzar InvalidStateException")
    void updateState_InvalidTransitionResueltoToAbierto_ShouldThrowException() {
        // ARRANGE
        TicketResponseDTO ticketResuelto = new TicketResponseDTO(
                validTicketId,
                validEmployeeId,
                validCategory,
                "Ticket",
                "Descripción",
                Priority.ALTA,
                State.RESUELTO,
                LocalDateTime.now(),
                LocalDateTime.now());

        TicketUpdateStateDTO updateDTO = new TicketUpdateStateDTO(State.ABIERTO);

        when(ticketDAO.findById(validTicketId)).thenReturn(Optional.of(ticketResuelto));

        // ACT & ASSERT
        assertThatThrownBy(() -> ticketService.updateState(validTicketId, updateDTO))
                .isInstanceOf(InvalidStateException.class)
                .hasMessageContaining("solo se puede cambiar a 'Cerrado' o 'En progreso'");

        verify(ticketRecordService, never()).create(any());
        verify(ticketDAO, never()).updateState(anyLong(), any());
    }

    // ==================== DELETE TICKET TESTS ====================

    @Test
    @DisplayName("DELETE - Ticket existente debe eliminar correctamente")
    void deleteTicket_ExistingTicket_ShouldDelete() {
        // ARRANGE
        when(ticketDAO.existsById(validTicketId)).thenReturn(true);

        // ACT
        ticketService.deleteTicket(validTicketId);

        // ASSERT
        verify(ticketDAO, times(1)).existsById(validTicketId);
        verify(ticketDAO, times(1)).deleteById(validTicketId);
    }

    @Test
    @DisplayName("DELETE - Ticket no existe debe lanzar ResourceNotFoundException")
    void deleteTicket_NonExistentTicket_ShouldThrowException() {
        // ARRANGE
        when(ticketDAO.existsById(validTicketId)).thenReturn(false);

        // ACT & ASSERT
        assertThatThrownBy(() -> ticketService.deleteTicket(validTicketId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Tiquete no encontrado con ID");

        verify(ticketDAO, times(1)).existsById(validTicketId);
        verify(ticketDAO, never()).deleteById(anyLong());
    }

    // ==================== GET TICKET BY ID TESTS ====================

    @Test
    @DisplayName("GET BY ID - Ticket existente debe retornar ticket")
    void getTicketById_ExistingTicket_ShouldReturnTicket() {
        // ARRANGE
        when(ticketDAO.findById(validTicketId)).thenReturn(Optional.of(validTicketResponse));

        // ACT
        TicketResponseDTO result = ticketService.getTicketById(validTicketId);

        // ASSERT
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(validTicketId);
        assertThat(result.getTitle()).isEqualTo("Error en inicio de sesión");

        verify(ticketDAO, times(1)).findById(validTicketId);
    }

    @Test
    @DisplayName("GET BY ID - Ticket no existe debe lanzar ResourceNotFoundException")
    void getTicketById_NonExistentTicket_ShouldThrowException() {
        // ACT & ASSERT
        assertThatThrownBy(() -> ticketService.getTicketById(validTicketId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Tiquete no encontrado con ID");

        verify(ticketDAO, times(1)).findById(anyLong());
    }

    // ==================== GET ALL TICKETS TESTS ====================

    @Test
    @DisplayName("GET ALL - Debe retornar lista de todos los tickets")
    void getAllTickets_ShouldReturnAllTickets() {
        // ARRANGE
        List<TicketResponseDTO> expectedList = Arrays.asList(
                validTicketResponse,
                new TicketResponseDTO(2L, validEmployeeId, validCategory, "Otro ticket", "Descripción",
                        Priority.MEDIA, State.EN_PROGRESO, LocalDateTime.now(), null));

        when(ticketDAO.findAll()).thenReturn(expectedList);

        // ACT
        List<TicketResponseDTO> result = ticketService.getAllTickets();

        // ASSERT
        assertThat(result).hasSize(2);
        assertThat(result).isEqualTo(expectedList);

        verify(ticketDAO, times(1)).findAll();
    }

    // ==================== GET TICKETS BY STATE TESTS ====================

    @Test
    @DisplayName("GET BY STATE - Debe retornar tickets filtrados por estado")
    void getTicketsByState_ShouldReturnFilteredTickets() {
        // ARRANGE
        List<TicketResponseDTO> expectedList = Arrays.asList(validTicketResponse);

        when(ticketDAO.findByState(State.ABIERTO)).thenReturn(expectedList);

        // ACT
        List<TicketResponseDTO> result = ticketService.getTicketsByState(State.ABIERTO);

        // ASSERT
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getState()).isEqualTo(State.ABIERTO);

        verify(ticketDAO, times(1)).findByState(State.ABIERTO);
    }

    // ==================== GET COMMENTS BY TICKET ID TESTS ====================

    @Test
    @DisplayName("GET COMMENTS - Debe retornar comentarios del ticket")
    void getAllCommentsByTicketId_ExistingTicket_ShouldReturnComments() {
        // ARRANGE
        List<CommentResponseDTO> expectedComments = Arrays.asList(
                new CommentResponseDTO(1L, validTicketId, validEmployee, "Comentario 1", LocalDateTime.now()),
                new CommentResponseDTO(2L, validTicketId, validEmployee, "Comentario 2", LocalDateTime.now()));

        when(ticketDAO.existsById(validTicketId)).thenReturn(true);
        when(commentDAO.findAllByTicketId(validTicketId)).thenReturn(expectedComments);

        // ACT
        List<CommentResponseDTO> result = ticketService.getAllCommentsByTicketId(validTicketId);

        // ASSERT
        assertThat(result).hasSize(2);
        assertThat(result).isEqualTo(expectedComments);

        verify(ticketDAO, times(1)).existsById(validTicketId);
        verify(commentDAO, times(1)).findAllByTicketId(validTicketId);
    }

    @Test
    @DisplayName("GET COMMENTS - Ticket no existe debe lanzar ResourceNotFoundException")
    void getAllCommentsByTicketId_NonExistentTicket_ShouldThrowException() {
        // ARRANGE
        when(ticketDAO.existsById(validTicketId)).thenReturn(false);

        // ACT & ASSERT
        assertThatThrownBy(() -> ticketService.getAllCommentsByTicketId(validTicketId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Tiquete no encontrado con ID");

        verify(commentDAO, never()).findAllByTicketId(anyLong());
    }

    // ==================== GET TICKET RECORDS BY TICKET ID TESTS ====================

    @Test
    @DisplayName("GET RECORDS - Debe retornar registros del ticket en rango de fechas")
    void getAllTicketRecordsByTicketId_ExistingTicket_ShouldReturnRecords() {
        // ARRANGE
        LocalDate from = LocalDate.of(2025, 1, 1);
        LocalDate to = LocalDate.of(2025, 12, 31);

        List<TicketRecordResponseDTO> expectedRecords = Arrays.asList(
                new TicketRecordResponseDTO(1L, "Abierto", "En progreso",
                        LocalDateTime.now()),
                new TicketRecordResponseDTO(2L, "En progreso", "Resuelto",
                        LocalDateTime.now()));

        when(ticketDAO.existsById(validTicketId)).thenReturn(true);
        when(ticketRecordService.getByDateRange(validTicketId, from, to)).thenReturn(expectedRecords);

        // ACT
        List<TicketRecordResponseDTO> result = ticketService.getAllTicketRecordsByTicketId(validTicketId, from, to);

        // ASSERT
        assertThat(result).hasSize(2);
        assertThat(result).isEqualTo(expectedRecords);

        verify(ticketDAO, times(1)).existsById(validTicketId);
        verify(ticketRecordService, times(1)).getByDateRange(validTicketId, from, to);
    }

    @Test
    @DisplayName("GET RECORDS - Ticket no existe debe lanzar ResourceNotFoundException")
    void getAllTicketRecordsByTicketId_NonExistentTicket_ShouldThrowException() {
        // ARRANGE
        LocalDate from = LocalDate.of(2025, 1, 1);
        LocalDate to = LocalDate.of(2025, 12, 31);

        when(ticketDAO.existsById(validTicketId)).thenReturn(false);

        // ACT & ASSERT
        assertThatThrownBy(() -> ticketService.getAllTicketRecordsByTicketId(validTicketId, from, to))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Tiquete no encontrado con ID");

        verify(ticketRecordService, never()).getByDateRange(anyLong(), any(), any());
    }
}
