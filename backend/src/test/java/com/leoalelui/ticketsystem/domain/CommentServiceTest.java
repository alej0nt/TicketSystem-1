package com.leoalelui.ticketsystem.domain;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.leoalelui.ticketsystem.domain.dto.request.CommentCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.CategoryResponseDTO;
import com.leoalelui.ticketsystem.domain.dto.response.CommentResponseDTO;
import com.leoalelui.ticketsystem.domain.dto.response.EmployeeResponseDTO;
import com.leoalelui.ticketsystem.domain.dto.response.TicketResponseDTO;
import com.leoalelui.ticketsystem.domain.exception.ResourceNotFoundException;
import com.leoalelui.ticketsystem.domain.service.impl.CommentServiceImpl;
import com.leoalelui.ticketsystem.domain.service.impl.EmployeeServiceImpl;
import com.leoalelui.ticketsystem.domain.service.impl.TicketServiceImpl;
import com.leoalelui.ticketsystem.persistence.dao.CommentDAO;
import com.leoalelui.ticketsystem.persistence.enums.Priority;
import com.leoalelui.ticketsystem.persistence.enums.Role;
import com.leoalelui.ticketsystem.persistence.enums.State;

@ExtendWith(MockitoExtension.class)
@DisplayName("CommentService - Unit Test")
public class CommentServiceTest {

    @Mock
    private CommentDAO commentDAO;

    @Mock
    private TicketServiceImpl ticketService;

    @Mock
    private EmployeeServiceImpl employeeService;

    @InjectMocks
    private CommentServiceImpl commentService;

    private CommentCreateDTO createDTO;
    private CommentResponseDTO responseDTO;
    private EmployeeResponseDTO employeeResponseDTO;
    private TicketResponseDTO ticketResponseDTO;
    private CategoryResponseDTO categoryResponseDTO;

    private Long validCommentId;
    private Long validEmployeeId;
    private Long validTicketId;

    @BeforeEach
    void setUp() {
        validCommentId = 1L;
        validEmployeeId = 100L;
        validTicketId = 200L;

        createDTO = new CommentCreateDTO(validTicketId, validEmployeeId, "Este es un comentario de prueba");

        employeeResponseDTO = new EmployeeResponseDTO(
                validEmployeeId,
                "Juan Pérez",
                "juan.perez@example.com",
                Role.AGENT,
                "Soporte Técnico"
        );

        categoryResponseDTO = new CategoryResponseDTO(1L, "Tecnología", "Categoría de tecnología");

        ticketResponseDTO = new TicketResponseDTO(
                validTicketId,
                validEmployeeId,
                categoryResponseDTO,
                "Problema con el sistema",
                "Descripción del problema",
                Priority.ALTA,
                State.ABIERTO,
                LocalDateTime.now(),
                null
        );

        responseDTO = new CommentResponseDTO(
                validCommentId,
                validTicketId,
                employeeResponseDTO,
                "Este es un comentario de prueba",
                LocalDateTime.now()
        );
    }

    // ==================== SAVE TESTS ====================
    @Test
    @DisplayName("SAVE - Debe guardar un comentario cuando empleado y ticket existen")
    void save_ValidEmployeeAndTicket_ShouldSaveComment() {
        when(employeeService.getEmployeeById(validEmployeeId)).thenReturn(employeeResponseDTO);
        when(ticketService.getTicketById(validTicketId)).thenReturn(ticketResponseDTO);
        when(commentDAO.save(createDTO)).thenReturn(responseDTO);

        CommentResponseDTO result = commentService.save(createDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(validCommentId);
        assertThat(result.getText()).isEqualTo("Este es un comentario de prueba");
        assertThat(result.getEmployee().getId()).isEqualTo(validEmployeeId);
        assertThat(result.getTicketId()).isEqualTo(validTicketId);

        verify(employeeService, times(1)).getEmployeeById(validEmployeeId);
        verify(ticketService, times(1)).getTicketById(validTicketId);
        verify(commentDAO, times(1)).save(createDTO);
    }

    @Test
    @DisplayName("SAVE - Debe lanzar ResourceNotFoundException si el empleado no existe")
    void save_NonExistentEmployee_ShouldThrowException() {
        when(employeeService.getEmployeeById(validEmployeeId))
                .thenThrow(new ResourceNotFoundException("Empleado no encontrado con ID: " + validEmployeeId));

        assertThatThrownBy(() -> commentService.save(createDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Empleado no encontrado con ID:");

        verify(employeeService, times(1)).getEmployeeById(validEmployeeId);
        verify(ticketService, never()).getTicketById(any());
        verify(commentDAO, never()).save(any());
    }

    @Test
    @DisplayName("SAVE - Debe lanzar ResourceNotFoundException si el ticket no existe")
    void save_NonExistentTicket_ShouldThrowException() {
        when(employeeService.getEmployeeById(validEmployeeId)).thenReturn(employeeResponseDTO);
        when(ticketService.getTicketById(validTicketId))
                .thenThrow(new ResourceNotFoundException("Tiquete no encontrado con ID: " + validTicketId));

        assertThatThrownBy(() -> commentService.save(createDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Tiquete no encontrado con ID:");

        verify(employeeService, times(1)).getEmployeeById(validEmployeeId);
        verify(ticketService, times(1)).getTicketById(validTicketId);
        verify(commentDAO, never()).save(any());
    }

    // ==================== DELETE TESTS ====================
    @Test
    @DisplayName("DELETE - Debe eliminar comentario existente")
    void delete_ExistingId_ShouldDeleteComment() {
        when(commentDAO.findById(validCommentId)).thenReturn(Optional.of(responseDTO));

        commentService.delete(validCommentId);

        verify(commentDAO, times(1)).findById(validCommentId);
        verify(commentDAO, times(1)).delete(validCommentId);
    }

    @Test
    @DisplayName("DELETE - Debe lanzar ResourceNotFoundException si el comentario no existe")
    void delete_NonExistentId_ShouldThrowException() {
        when(commentDAO.findById(validCommentId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> commentService.delete(validCommentId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("No se encontro el comentario con el id:");

        verify(commentDAO, times(1)).findById(validCommentId);
        verify(commentDAO, never()).delete(any());
    }

    // ==================== FIND ALL BY TICKET ID TESTS ====================
    @Test
    @DisplayName("FIND ALL BY TICKET ID - Debe retornar lista de comentarios del ticket")
    void findAllByTicketId_ExistingTicket_ShouldReturnCommentList() {
        CommentResponseDTO comment2 = new CommentResponseDTO(
                2L,
                validTicketId,
                employeeResponseDTO,
                "Segundo comentario",
                LocalDateTime.now()
        );

        List<CommentResponseDTO> expectedList = List.of(responseDTO, comment2);

        when(ticketService.getTicketById(validTicketId)).thenReturn(ticketResponseDTO);
        when(commentDAO.findAllByTicketId(validTicketId)).thenReturn(expectedList);

        List<CommentResponseDTO> result = commentService.findAllByTicketId(validTicketId);

        assertThat(result).hasSize(2);
        assertThat(result).isEqualTo(expectedList);
        assertThat(result.get(0).getTicketId()).isEqualTo(validTicketId);
        assertThat(result.get(1).getTicketId()).isEqualTo(validTicketId);

        verify(ticketService, times(1)).getTicketById(validTicketId);
        verify(commentDAO, times(1)).findAllByTicketId(validTicketId);
    }

    @Test
    @DisplayName("FIND ALL BY TICKET ID - Debe retornar lista vacía si no hay comentarios")
    void findAllByTicketId_NoComments_ShouldReturnEmptyList() {
        when(ticketService.getTicketById(validTicketId)).thenReturn(ticketResponseDTO);
        when(commentDAO.findAllByTicketId(validTicketId)).thenReturn(List.of());

        List<CommentResponseDTO> result = commentService.findAllByTicketId(validTicketId);

        assertThat(result).isEmpty();
        verify(ticketService, times(1)).getTicketById(validTicketId);
        verify(commentDAO, times(1)).findAllByTicketId(validTicketId);
    }

    @Test
    @DisplayName("FIND ALL BY TICKET ID - Debe lanzar ResourceNotFoundException si el ticket no existe")
    void findAllByTicketId_NonExistentTicket_ShouldThrowException() {
        when(ticketService.getTicketById(validTicketId))
                .thenThrow(new ResourceNotFoundException("Tiquete no encontrado con ID: " + validTicketId));

        assertThatThrownBy(() -> commentService.findAllByTicketId(validTicketId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Tiquete no encontrado con ID:");

        verify(ticketService, times(1)).getTicketById(validTicketId);
        verify(commentDAO, never()).findAllByTicketId(any());
    }
}