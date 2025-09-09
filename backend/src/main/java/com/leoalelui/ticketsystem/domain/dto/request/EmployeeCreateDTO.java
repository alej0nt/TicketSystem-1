package com.leoalelui.ticketsystem.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeCreateDTO {
    @NotBlank(message = "El nombre del empleado no puede ser nulo o vacìo.")
    private String name;

    @NotBlank(message = "El email del empleado no puede ser nulo o vacìo.")
    @Email(message = "Formato de email invàlido.")
    private String email;

    @NotBlank(message = "La contrasña no pueder ser nula o vacia.")
    private String password;

    @NotBlank(message = "El departamento del empleado no puede ser nulo o vacìo.")
    private String department;
}
