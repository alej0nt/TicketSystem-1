package com.leoalelui.ticketsystem.persistence.mapper;

import com.leoalelui.ticketsystem.domain.dto.request.TicketCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.TicketResponseDTO;
import com.leoalelui.ticketsystem.persistence.entity.TicketEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "creation_date", ignore = true)
    TicketEntity toEntity(TicketCreateDTO ticketCreateDTO);

    TicketResponseDTO toResponseDTO(TicketEntity ticketEntity);
}
