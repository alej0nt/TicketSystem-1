package com.leoalelui.ticketsystem.domain.dto.response;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class EmployeeResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String role;
    private String department;

}
