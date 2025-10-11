package com.leoalelui.ticketsystem.persistence.mapper;

import com.leoalelui.ticketsystem.domain.dto.request.EvidenceCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.EvidenceResponseDTO;
import com.leoalelui.ticketsystem.persistence.entity.EvidenceEntity;
import com.leoalelui.ticketsystem.persistence.entity.TicketEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EvidenceMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ticket", source = "ticketId", qualifiedByName = "ticketIdToEntity")
    EvidenceEntity toEntity(EvidenceCreateDTO dto);

    @Mapping(target = "ticketId", source = "ticket.id")
    EvidenceResponseDTO toResponseDTO(EvidenceEntity entity);

    List<EvidenceResponseDTO> toDTOList(List<EvidenceEntity> entities);

    @Named("ticketIdToEntity")
    default TicketEntity ticketIdToEntity(Long ticketId) {
        if (ticketId == null) {
            return null;
        }
        TicketEntity ticket = new TicketEntity();
        ticket.setId(ticketId);
        return ticket;
    }
}
