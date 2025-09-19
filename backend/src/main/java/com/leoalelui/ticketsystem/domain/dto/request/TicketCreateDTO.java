package com.leoalelui.ticketsystem.domain.dto.request;

import com.leoalelui.ticketsystem.persistence.enums.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "El título no puede ser nulo o vacío.")
    @Size(min = 5, max = 100, message = "El título debe tener entre 5 y 100 caracteres.")
    private String title;

    @NotBlank(message = "La descripción no puede ser nula o vacía.")
    @Size(min = 10, max = 1000, message = "La descripción debe tener entre 10 y 1000 caracteres.")
    private String description;

    @NotNull(message = "El id de la categoría no puede ser nulo.")
    private Long categoryId;

    @NotNull(message = "La prioridad no puede ser nula.")
    private Priority priority;
}

