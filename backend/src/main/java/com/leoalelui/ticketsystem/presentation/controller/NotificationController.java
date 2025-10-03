package com.leoalelui.ticketsystem.presentation.controller;

import com.leoalelui.ticketsystem.domain.dto.response.NotificationResponseDTO;
import com.leoalelui.ticketsystem.domain.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * Controller para manejar las notificaciones
 *
 * @author leona
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/notifications")
@Tag(name = "Notificaciones", description = "API para la gestión de notificaciones")
public class NotificationController {
    private final NotificationService notificationService;
    
    @Operation(summary = "Obtener notificaciones de un empleado",
            description = "Devuelve todas las notificaciones de un empleado específico.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de notificaciones obtenida exitosamente",
                content = @Content(schema = @Schema(implementation = NotificationResponseDTO.class))),
        @ApiResponse(responseCode = "401", description = "No autorizado"),
        @ApiResponse(responseCode = "403", description = "Prohibido - rol insuficiente")
    })
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<NotificationResponseDTO>> getNotificationsByEmployee(
            @PathVariable @Parameter(description = "ID del empleado con sus notificaciones") Long employeeId) {
        List<NotificationResponseDTO> notifications = notificationService.getNotificationsByEmployee(employeeId);
        return ResponseEntity.ok(notifications);
    }

    @Operation(summary = "Marcar notificación como leída",
            description = "Actualiza el estado de una notificación para indicar que ya fue leída.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificación marcada como leída",
                content = @Content(schema = @Schema(implementation = NotificationResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Notificación no encontrada"),
        @ApiResponse(responseCode = "401", description = "No autorizado"),
        @ApiResponse(responseCode = "403", description = "Prohibido - rol insuficiente")
    })
    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<NotificationResponseDTO> markAsRead(@PathVariable @Parameter(description = "ID de la notificación a marcar") Long notificationId) {
        NotificationResponseDTO updated = notificationService.markAsRead(notificationId);
        return ResponseEntity.ok(updated);
    }
}
