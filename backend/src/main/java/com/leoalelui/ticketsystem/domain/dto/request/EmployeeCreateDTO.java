package com.leoalelui.ticketsystem.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeCreateDTO {
    @NotBlank(message = "El nombre no puede ser nulo o vacìo.")
    private String name;

    @NotBlank(message = "El email no puede ser nulo o vacìo.")
    @Email(message = "Formato de email invàlido.")
    private String email;

    @NotBlank(message = "La contraseña no puede ser nula o vacia.")
    private String password;

    @NotBlank(message = "El departamento no puede ser nulo o vacìo.")
    private String department;
}
