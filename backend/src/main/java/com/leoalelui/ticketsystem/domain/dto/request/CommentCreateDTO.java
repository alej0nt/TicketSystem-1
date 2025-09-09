package com.leoalelui.ticketsystem.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO de creación para comentarios
 * @author Leonardo Argoty
 */
@Data
@AllArgsConstructor
public class CommentCreateDTO {

    @NotNull(message = "El id del ticket no puede ser nulo.")
    private Long ticketId;

    @NotNull(message = "El id del empleado no puede ser nulo.")
    private Long employeeId;

    @NotBlank(message = "El texto no puede ser nulo o vacío.")
    private String text;
}
