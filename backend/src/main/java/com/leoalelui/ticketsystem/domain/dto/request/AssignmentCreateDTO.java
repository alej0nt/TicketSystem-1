package com.leoalelui.ticketsystem.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @author Leonardo Argoty
 */
@Data
@AllArgsConstructor
public class AssignmentCreateDTO {

    @NotNull(message = "El id del ticket no puede ser nulo.")
    private Long ticketId;

    @NotNull(message = "El id del empleado no puede ser nulo.")
    private Long employeeId;
}
