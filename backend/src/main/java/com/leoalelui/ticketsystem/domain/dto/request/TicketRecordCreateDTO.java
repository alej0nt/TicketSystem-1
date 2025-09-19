package com.leoalelui.ticketsystem.domain.dto.request;

import com.leoalelui.ticketsystem.persistence.enums.State;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * DTO de creación para registros de tickets , cuando hay algun cambio en su estado
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
    
    @NotNull(message = "El estado previo no puede ser nulo.")
    @Schema(description = "Estado previo del ticket antes del cambio",
            example = "ABIERTO")
    private State previousState;

    @NotNull(message = "El estado siguiente no puede ser nulo.")
    @Schema(description = "Nuevo estado del ticket después del cambio",
            example = "EN_PROGRESO")
    private State nextState;
}


