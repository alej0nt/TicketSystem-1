package com.leoalelui.ticketsystem.persistence.mapper;

import com.leoalelui.ticketsystem.domain.dto.request.TicketRecordCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.TicketRecordResponseDTO;
import com.leoalelui.ticketsystem.persistence.entity.TicketRecordEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TicketMapper.class})
public interface TicketRecordMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "changedDate", ignore = true)
    @Mapping(target="ticket", source="ticketId", qualifiedByName = "createTicketEntityFromId")
    TicketRecordEntity toEntity(TicketRecordCreateDTO ticketRecordCreateDTO);

    TicketRecordResponseDTO toResponseDTO(TicketRecordEntity ticketRecordEntity);
    List<TicketRecordResponseDTO> toDTOList(List<TicketRecordEntity> entities);
}
