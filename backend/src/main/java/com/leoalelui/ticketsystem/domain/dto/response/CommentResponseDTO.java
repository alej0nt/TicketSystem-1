package com.leoalelui.ticketsystem.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * DTO de respuesta para comentarios
 * @author Leonardo Argoty
 */
@Data
@AllArgsConstructor
public class CommentResponseDTO {
    private Long id;
    private TicketResponseDTO ticket;
    private EmployeeResponseDTO employee;
    private String text;
    private LocalDateTime createdAt;
}

