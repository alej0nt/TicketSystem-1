package com.leoalelui.ticketsystem.domain.dto.request;

import com.leoalelui.ticketsystem.persistence.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeUpdateDTO {
    @NotBlank(message = "El nombre del empleado no puede ser nulo o vacío.")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres.")
    private String name;

    @NotBlank(message = "El email del empleado no puede ser nulo o vacío.")
    @Email(message = "Formato de email inválido.")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres.")
    private String email;

    @NotBlank(message = "El role del empleado no puede ser nulo o vacío.")
    @Size(min = 2, max = 20, message = "El role debe tener entre 2 y 20 caracteres.")
    private Role role;

    @NotBlank(message = "El departamento del empleado no puede ser nulo o vacío.")
    @Size(min = 2, max = 50, message = "El departamento debe tener entre 2 y 50 caracteres.")
    private String department;
}
