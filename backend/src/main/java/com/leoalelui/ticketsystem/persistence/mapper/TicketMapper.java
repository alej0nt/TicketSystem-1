package com.leoalelui.ticketsystem.persistence.mapper;

import com.leoalelui.ticketsystem.domain.dto.request.TicketCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.TicketResponseDTO;
import com.leoalelui.ticketsystem.persistence.entity.TicketEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(
        componentModel = "spring",
        uses = {EmployeeMapper.class, CategoryMapper.class, CommentMapper.class, TicketRecordMapper.class}
)
public interface TicketMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "employee", source = "employeeId", qualifiedByName = "createEmployeeEntityFromId")
    @Mapping(target = "category", source = "categoryId", qualifiedByName = "createCategoryEntityFromId")
    TicketEntity toEntity(TicketCreateDTO ticketCreateDTO);

    @Mapping(target = "employeeId", source = "employee.id")
    TicketResponseDTO toResponseDTO(TicketEntity ticketEntity);

    @Named("createTicketEntityFromId")
    default TicketEntity createTicketEntityFromId(Long ticketId) {
        if (ticketId == null) return null;
        TicketEntity ticket = new TicketEntity();
        ticket.setId(ticketId);
        return ticket;
    }
}
