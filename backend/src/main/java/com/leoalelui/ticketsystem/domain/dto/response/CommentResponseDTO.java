package com.leoalelui.ticketsystem.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * DTO de respuesta para comentarios
 * @author Leonardo Argoty
 */
@Data
@AllArgsConstructor
@Schema(description = "Objeto de respuesta que contiene los datos de un comentario en un ticket")
public class CommentResponseDTO {

    @Schema(description = "Identificador único del comentario", example = "101")
    private Long id;

    @Schema(description = "Identificador único del ticket al que pertenece el comentario", example = "55")
    private Long ticketId;

    @Schema(description = "Información del empleado que realizó el comentario")
    private EmployeeResponseDTO employee;

    @Schema(description = "Texto del comentario realizado", example = "El ticket fue revisado y se asignó al equipo de soporte.")
    private String text;

    @Schema(description = "Fecha y hora de creación del comentario", example = "2025-09-13T14:30:00")
    private LocalDateTime createdAt;
}

