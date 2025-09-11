package com.leoalelui.ticketsystem.domain.dto.response;

import lombok.Data;
import lombok.AllArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@Schema(description = "Representa la información de un empleado dentro del sistema")
public class EmployeeResponseDTO {

    @Schema(description = "Identificador único del empleado", example = "101")
    private Long id;

    @Schema(description = "Nombre completo del empleado", example = "Carlos Pérez")
    private String name;

    @Schema(description = "Correo electrónico del empleado", example = "carlos.perez@empresa.com")
    private String email;

    @Schema(description = "Rol que desempeña el empleado", example = "ADMIN")
    private String role;

    @Schema(description = "Departamento al que pertenece el empleado", example = "Soporte Técnico")
    private String department;

    //private List<AssignmentResponseDTO> assignments;
    //private List<CommentResponseDTO> comments;
    //private List<NotificationResponseDTO> notifications;
}
