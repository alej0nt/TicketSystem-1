package com.leoalelui.ticketsystem.domain.dto.response;


import com.leoalelui.ticketsystem.persistence.entity.CommentEntity;
import com.leoalelui.ticketsystem.persistence.entity.TicketRecordEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

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
    private List<CommentResponseDTO> comments;
    private List<TicketRecordResponseDTO> ticketRecords;
}

