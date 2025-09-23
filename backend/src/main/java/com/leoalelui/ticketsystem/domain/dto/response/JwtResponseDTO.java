package com.leoalelui.ticketsystem.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@Schema(description = "Respuesta de autenticación con JWT")
public class JwtResponseDTO {
    
    @Schema(description = "Token JWT de acceso", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;
    
    @Schema(description = "Tipo de token", example = "Bearer")
    private String type = "Bearer";
    
    @Schema(description = "Información del empleado autenticado")
    private EmployeeResponseDTO employee;

    public JwtResponseDTO(String token, EmployeeResponseDTO employee) {
        this.token = token;
        this.employee = employee;
    }
}
