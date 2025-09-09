package com.leoalelui.ticketsystem.domain.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
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
}

