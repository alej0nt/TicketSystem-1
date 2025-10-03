package com.leoalelui.ticketsystem.domain;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

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
import com.leoalelui.ticketsystem.domain.dto.response.EmployeeResponseDTO;
import com.leoalelui.ticketsystem.domain.dto.response.NotificationResponseDTO;
import com.leoalelui.ticketsystem.domain.exception.ResourceNotFoundException;
import com.leoalelui.ticketsystem.domain.service.impl.NotificationServiceImpl;
import com.leoalelui.ticketsystem.persistence.dao.EmployeeDAO;
import com.leoalelui.ticketsystem.persistence.dao.NotificationDAO;

@ExtendWith(MockitoExtension.class)
@DisplayName("NotificationService - Unit Test")
public class NotificationServiceTest {
    @Mock
    private NotificationDAO notificationDAO;

    @Mock
    private EmployeeDAO employeeDAO;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    private NotificationCreateDTO validNotificationCreateDTO;
    private NotificationResponseDTO validNotificationResponse;
    private Long validEmployeeId;
    private Long validNotificationId;

    @BeforeEach
    public void setUp() {
        validEmployeeId = 1L;
        validNotificationId = 1L;

        validNotificationCreateDTO = new NotificationCreateDTO("Acaba de agregar un nuevo ticket", validEmployeeId);
        validNotificationResponse = new NotificationResponseDTO(validNotificationId, "Acaba de agregar un nuevo ticket",
                LocalDateTime.now(), false);
    }
    // ==================== CREATE NOTIFICATION TESTS ====================
    @Test
    @DisplayName("CREATE - Notificación válida debe crear notificación")
    void createNotification_ValidData_ShouldCreateNotification() {
        when(employeeDAO.findById(validEmployeeId)).thenReturn(Optional.ofNullable(mock(EmployeeResponseDTO.class)));
        when(notificationDAO.create(any(NotificationCreateDTO.class))).thenReturn(validNotificationResponse);

        NotificationResponseDTO result = notificationService.create(validNotificationCreateDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(validNotificationId);
        assertThat(result.getMessage()).isEqualTo("Acaba de agregar un nuevo ticket");
        assertThat(result.isRead()).isFalse();

        verify(employeeDAO, times(1)).findById(validEmployeeId);
        verify(notificationDAO, times(1)).create(any(NotificationCreateDTO.class));
    }

    @Test
    @DisplayName("CREATE - Empleado no existente debe lanzar ResourceNotFoundException")
    void createNotification_NonExistentEmployee_ShouldThrowResourceNotFoundException() {
        when(employeeDAO.findById(validEmployeeId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> notificationService.create(validNotificationCreateDTO))
                .isInstanceOf(ResourceNotFoundException.class).hasMessageContaining("Empleado no encontrado con id:");

        verify(employeeDAO, times(1)).findById(validEmployeeId);
        verify(notificationDAO, never()).create(any(NotificationCreateDTO.class));
    }

    // ==================== GET NOTIFICATIONS BY EMPLOYEE TESTS ====================
    @Test
    @DisplayName("GET BY EMPLOYEE - Debe retornar lista de notificaciones del empleado")
    void getNotificationsByEmployee_ValidEmployee_ShouldReturnNotifications() {
        List<NotificationResponseDTO> expectedList = Arrays.asList(
                new NotificationResponseDTO(1L, "Notificación 1", LocalDateTime.now(), false),
                new NotificationResponseDTO(2L, "Notificación 2", LocalDateTime.now(), true));

        when(employeeDAO.findById(validEmployeeId)).thenReturn(Optional.of(mock(EmployeeResponseDTO.class)));
        when(notificationDAO.findByEmployeeId(validEmployeeId)).thenReturn(expectedList);

        List<NotificationResponseDTO> result = notificationService.getNotificationsByEmployee(validEmployeeId);

        assertThat(result).hasSize(2);
        assertThat(result).isEqualTo(expectedList);
        verify(employeeDAO, times(1)).findById(validEmployeeId);
        verify(notificationDAO, times(1)).findByEmployeeId(validEmployeeId);
    }

    @Test
    @DisplayName("GET BY EMPLOYEE - Empleado inexistente debe lanzar ResourceNotFoundException")
    void getNotificationsByEmployee_EmployeeNotFound_ShouldThrowResourceNotFoundException() {
        when(employeeDAO.findById(validEmployeeId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> notificationService.getNotificationsByEmployee(validEmployeeId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Empleado no encontrado con id:");

        verify(employeeDAO, times(1)).findById(validEmployeeId);
        verify(notificationDAO, never()).findByEmployeeId(validEmployeeId);
    }

    // ==================== MARK AS READ TESTS ====================
    @Test
    @DisplayName("MARK AS READ - Debe marcar notificación como leída")
    void markAsRead_ExistingNotification_ShouldMarkAsRead() {
        NotificationResponseDTO readNotification = new NotificationResponseDTO(
                validNotificationId, "Notificación leída", LocalDateTime.now(), true);

        when(notificationDAO.existsById(validNotificationId)).thenReturn(true);
        when(notificationDAO.markAsRead(validNotificationId)).thenReturn(readNotification);

        NotificationResponseDTO result = notificationService.markAsRead(validNotificationId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(validNotificationId);
        assertThat(result.isRead()).isTrue();
        verify(notificationDAO, times(1)).existsById(validNotificationId);
        verify(notificationDAO, times(1)).markAsRead(validNotificationId);
    }

    @Test
    @DisplayName("MARK AS READ - Notificación inexistente debe lanzar ResourceNotFoundException")
    void markAsRead_NotificationNotFound_ShouldThrowResourceNotFoundException() {
        when(notificationDAO.existsById(validNotificationId)).thenReturn(false);

        assertThatThrownBy(() -> notificationService.markAsRead(validNotificationId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("No se ha encontrado la notificacion con id:");

        verify(notificationDAO, times(1)).existsById(validNotificationId);
        verify(notificationDAO, never()).markAsRead(validNotificationId);
    }
}
