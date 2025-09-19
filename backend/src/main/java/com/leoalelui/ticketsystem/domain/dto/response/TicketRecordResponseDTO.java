package com.leoalelui.ticketsystem.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO de respuesta para registros de tickets
 * @author Leonardo Argoty
 */
@Data
@AllArgsConstructor
@Schema(description = "Datos de respuesta que representan un registro de cambio en un ticket")
public class TicketRecordResponseDTO {

    @Schema(description = "ID único del registro de ticket", example = "45")
    private Long id;
//    @Schema(description = "Id del ticket asociado al registro")
//    private Long ticketId;

    @Schema(description = "Estado previo del ticket antes del cambio", example = "Abierto")
    private String previousState;

    @Schema(description = "Estado nuevo del ticket después del cambio", example = "En progreso")
    private String nextState;

    @Schema(description = "Fecha y hora en que se registró el cambio de estado", example = "2025-09-10T14:30:00")
    private LocalDateTime changedDate;
}

