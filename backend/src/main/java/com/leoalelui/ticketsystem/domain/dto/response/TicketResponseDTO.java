package com.leoalelui.ticketsystem.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 
 * @author Leonardo Argoty
 */
@Data
@AllArgsConstructor
public class TicketResponseDTO {
    private Long id;
    private EmployeeResponseDTO employee;
    private CategoryResponseDTO category;
    private String title;
    private String description;
    private String priority;
    private String state;
    private LocalDateTime creationDate;
    private LocalDateTime closingDate;
    // private List<CommentResponseDTO> comments;
    // private List<TicketRecordResponseDTO> ticketRecords;
}

