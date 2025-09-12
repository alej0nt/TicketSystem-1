package com.leoalelui.ticketsystem.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "DTO para actualizar un comentario")
public class CommentUpdateDTO {

    @Schema(description = "El contenido actualizado del comentario.",
            example = "Este es el comentario actualizado.")
    @NotBlank(message = "El comentario no puede estar vacio.")
    @Size(max = 500, message = "El comentario no puede tener más de 500 caracteres.")
    private String content;
}
