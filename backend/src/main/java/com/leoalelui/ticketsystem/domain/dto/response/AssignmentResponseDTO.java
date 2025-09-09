package com.leoalelui.ticketsystem.domain.dto.response;

import com.leoalelui.ticketsystem.persistence.entity.EmployeeEntity;
import com.leoalelui.ticketsystem.persistence.entity.TicketEntity;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @author Leonardo Argoty
 */

@Data
public class AssignmentResponseDTO {
    private Long id;

    private TicketEntity ticket;
    private EmployeeEntity employee;
    private LocalDateTime assignment_date;
}

