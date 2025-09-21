package com.leoalelui.ticketsystem.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author leona
 */
@Data
@AllArgsConstructor
public class LoginRequestDTO {
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Formato de email inválido")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password; 
}
