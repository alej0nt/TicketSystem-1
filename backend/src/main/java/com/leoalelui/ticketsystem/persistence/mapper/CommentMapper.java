// ============================================
// CommentMapper.java - CORREGIDO
// ============================================
package com.leoalelui.ticketsystem.persistence.mapper;

import com.leoalelui.ticketsystem.domain.dto.request.CommentCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.CommentResponseDTO;
import com.leoalelui.ticketsystem.persistence.entity.CommentEntity;
import com.leoalelui.ticketsystem.persistence.entity.EmployeeEntity;
import com.leoalelui.ticketsystem.persistence.entity.TicketEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface CommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "ticket", source = "ticketId", qualifiedByName = "ticketIdToEntity")
    @Mapping(target = "employee", source = "employeeId", qualifiedByName = "employeeIdToEntity")
    CommentEntity toEntity(CommentCreateDTO commentCreateDTO);

    @Mapping(target = "ticketId", source = "ticket.id")
    CommentResponseDTO toResponseDTO(CommentEntity commentEntity);

    @Named("ticketIdToEntity")
    default TicketEntity ticketIdToEntity(Long ticketId) {
        if (ticketId == null) {
            return null;
        }
        TicketEntity ticket = new TicketEntity();
        ticket.setId(ticketId);
        return ticket;
    }

    @Named("employeeIdToEntity")
    default EmployeeEntity employeeIdToEntity(Long employeeId) {
        if (employeeId == null) {
            return null;
        }
        EmployeeEntity employee = new EmployeeEntity();
        employee.setId(employeeId);
        return employee;
    }
}