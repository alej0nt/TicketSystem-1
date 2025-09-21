package com.leoalelui.ticketsystem.persistence.mapper;

import com.leoalelui.ticketsystem.domain.dto.request.TicketRecordCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.TicketRecordResponseDTO;
import com.leoalelui.ticketsystem.persistence.entity.TicketRecordEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-21T16:15:09-0500",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class TicketRecordMapperImpl implements TicketRecordMapper {

    @Autowired
    private TicketMapper ticketMapper;

    @Override
    public TicketRecordEntity toEntity(TicketRecordCreateDTO ticketRecordCreateDTO) {
        if ( ticketRecordCreateDTO == null ) {
            return null;
        }

        TicketRecordEntity ticketRecordEntity = new TicketRecordEntity();

        ticketRecordEntity.setTicket( ticketMapper.createTicketEntityFromId( ticketRecordCreateDTO.getTicketId() ) );
        if ( ticketRecordCreateDTO.getNextState() != null ) {
            ticketRecordEntity.setNextState( ticketRecordCreateDTO.getNextState().name() );
        }
        if ( ticketRecordCreateDTO.getPreviousState() != null ) {
            ticketRecordEntity.setPreviousState( ticketRecordCreateDTO.getPreviousState().name() );
        }

        return ticketRecordEntity;
    }

    @Override
    public TicketRecordResponseDTO toResponseDTO(TicketRecordEntity ticketRecordEntity) {
        if ( ticketRecordEntity == null ) {
            return null;
        }

        LocalDateTime changedDate = null;
        Long id = null;
        String nextState = null;
        String previousState = null;

        changedDate = ticketRecordEntity.getChangedDate();
        id = ticketRecordEntity.getId();
        nextState = ticketRecordEntity.getNextState();
        previousState = ticketRecordEntity.getPreviousState();

        TicketRecordResponseDTO ticketRecordResponseDTO = new TicketRecordResponseDTO( id, previousState, nextState, changedDate );

        return ticketRecordResponseDTO;
    }

    @Override
    public List<TicketRecordResponseDTO> toDTOList(List<TicketRecordEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<TicketRecordResponseDTO> list = new ArrayList<TicketRecordResponseDTO>( entities.size() );
        for ( TicketRecordEntity ticketRecordEntity : entities ) {
            list.add( toResponseDTO( ticketRecordEntity ) );
        }

        return list;
    }
}
