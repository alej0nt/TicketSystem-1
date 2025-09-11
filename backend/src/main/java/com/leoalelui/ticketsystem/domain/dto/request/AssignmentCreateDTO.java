package com.leoalelui.ticketsystem.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @author Leonardo Argoty
 */
@Data
@AllArgsConstructor
@Schema(description = "Datos para crear una nueva asignación de ticket")
public class AssignmentCreateDTO {
    @NotNull(message = "El id del ticket no puede ser nulo.")
    @Schema(description = "ID del ticket a asignar", example = "123")
    private Long ticketId;

    @NotNull(message = "El id del empleado no puede ser nulo.")
    @Schema(description = "ID del empleado que recibe la asignación", example = "7")
    private Long employeeId;
}

