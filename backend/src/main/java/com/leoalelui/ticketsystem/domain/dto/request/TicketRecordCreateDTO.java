package com.leoalelui.ticketsystem.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO de creación para registros de tickets
 * Representa un cambio de estado en el historial de un ticket.
 * Incluye el ticket afectado, el estado anterior y el nuevo estado.
 * 
 * @author Leonardo Argoty
 */
@Data
@AllArgsConstructor
@Schema(description = "Datos para registrar un cambio de estado en un ticket")
public class TicketRecordCreateDTO {

    @NotNull(message = "El id del ticket no puede ser nulo.")
    @Schema(description = "ID del ticket al que pertenece el registro",
            example = "123")
    private Long ticketId;

    @NotBlank(message = "El estado previo no puede ser nulo o vacío.")
    @Schema(description = "Estado previo del ticket antes del cambio",
            example = "Abierto")
    private String previousState;

    @NotBlank(message = "El estado siguiente no puede ser nulo o vacío.")
    @Schema(description = "Nuevo estado del ticket después del cambio",
            example = "En progreso")
    private String nextState;
}


