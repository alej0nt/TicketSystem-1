package com.leoalelui.ticketsystem.domain.dto.response;

import com.leoalelui.ticketsystem.persistence.enums.Priority;
import com.leoalelui.ticketsystem.persistence.enums.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(description = "Representa la información de un ticket en el sistema")
public class TicketResponseDTO {
    @Schema(description = "Identificador único del ticket", example = "5001")
    private Long id;

    @Schema(description = "ID del empleado asignado al ticket")
    private Long employeeId;

    @Schema(description = "Categoría a la que pertenece el ticket")
    private CategoryResponseDTO category;

    @Schema(description = "Título breve y descriptivo del ticket", example = "Error en inicio de sesión")
    private String title;

    @Schema(description = "Descripción detallada del problema o solicitud",
            example = "El usuario no puede iniciar sesión con sus credenciales correctas.")
    private String description;

    @Schema(description = "Prioridad del ticket", example = "ALTA")
    private Priority priority;

    @Schema(description = "Estado actual del ticket", example = "ABIERTO")
    private State state;

    @Schema(description = "Fecha de creación del ticket", example = "2025-09-11T14:30:00")
    private LocalDateTime creationDate;

    @Schema(description = "Fecha de cierre del ticket (puede ser null si no está cerrado)",
            example = "2025-09-12T18:00:00")
    private LocalDateTime closingDate;
}


