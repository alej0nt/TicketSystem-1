package com.leoalelui.ticketsystem.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO de creación para registros de tickets
 * @author Leonardo Argoty
 */
@Data
@AllArgsConstructor
public class TicketRecordCreateDTO {
    @NotNull(message = "El id del ticket no puede ser nulo.")
    private Long ticketId;

    @NotBlank(message = "El estado previo no puede ser nulo o vacío.")
    private String previousState;

    @NotBlank(message = "El estado siguiente no puede ser nulo o vacío.")
    private String nextState;
}

