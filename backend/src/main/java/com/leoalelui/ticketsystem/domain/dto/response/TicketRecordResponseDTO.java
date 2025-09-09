package com.leoalelui.ticketsystem.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO de respuesta para registros de tickets
 * @author Leonardo Argoty
 */
@Data
@AllArgsConstructor
public class TicketRecordResponseDTO {
    private Long id;
    private TicketResponseDTO ticket;
    private String previousState;
    private String nextState;
    private LocalDateTime changedDate;
}
