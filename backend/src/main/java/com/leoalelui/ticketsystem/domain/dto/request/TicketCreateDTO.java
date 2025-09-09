package com.leoalelui.ticketsystem.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @author Leonardo Argoty
 */
@Data
@AllArgsConstructor
public class TicketCreateDTO {

    @NotNull(message = "El id del empleado no puede ser nulo.")
    private Long employeeId;

    @NotNull(message = "El id de la categoría no puede ser nulo.")
    private Long categoryId;

    @NotBlank(message = "El título no puede ser nulo o vacío.")
    private String title;

    @NotBlank(message = "La descripción no puede ser nula o vacía.")
    private String description;

    @NotBlank(message = "La prioridad no puede ser nula o vacía.")
    private String priority;
}

