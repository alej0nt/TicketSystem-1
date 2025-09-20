package com.leoalelui.ticketsystem.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeCreateDTO {
    @NotBlank(message = "El nombre no puede ser nulo o vacío.")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres.")
    private String name;

    @NotBlank(message = "El email no puede ser nulo o vacío.")
    @Email(message = "Formato de email inválido.")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres.")
    private String email;

    @NotBlank(message = "La contraseña no puede ser nula o vacía.")
    @Size(min = 8, max = 50, message = "La contraseña debe tener entre 8 y 50 caracteres.")
    private String password;

    @NotBlank(message = "El departamento no puede ser nulo o vacío.")
    @Size(min = 2, max = 50, message = "El departamento debe tener entre 2 y 50 caracteres.")
    private String department;
}
