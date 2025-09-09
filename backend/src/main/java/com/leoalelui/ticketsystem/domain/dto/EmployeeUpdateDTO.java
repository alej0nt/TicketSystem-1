package com.leoalelui.ticketsystem.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeUpdateDTO {

    @NotBlank(message = "El nombre del empleado no puede ser nulo o vacío.")
    private String name;

    @NotBlank(message = "El email del empleado no puede ser nulo o vacìo.")
    @Email(message = "Formato de email invàlido.")
    private String email;

    private String password;

    @NotBlank(message = "El role del empleado no puede ser nulo o vacío.")
    private String role;

    @NotBlank(message = "El departamento del empleado no puede ser nulo o vacío.")
    private String department;

}
