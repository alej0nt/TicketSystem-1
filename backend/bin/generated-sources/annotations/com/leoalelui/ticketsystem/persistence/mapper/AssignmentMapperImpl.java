package com.leoalelui.ticketsystem.persistence.mapper;

import com.leoalelui.ticketsystem.domain.dto.request.AssignmentCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.AssignmentResponseDTO;
import com.leoalelui.ticketsystem.domain.dto.response.EmployeeResponseDTO;
import com.leoalelui.ticketsystem.domain.dto.response.TicketResponseDTO;
import com.leoalelui.ticketsystem.persistence.entity.AssignmentEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-21T16:15:10-0500",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class AssignmentMapperImpl implements AssignmentMapper {

    @Autowired
    private TicketMapper ticketMapper;
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public AssignmentResponseDTO toDTO(AssignmentEntity entity) {
        if ( entity == null ) {
            return null;
        }

        LocalDateTime assignmentDate = null;
        EmployeeResponseDTO employee = null;
        Long id = null;
        TicketResponseDTO ticket = null;

        assignmentDate = entity.getAssignmentDate();
        employee = employeeMapper.toResponseDTO( entity.getEmployee() );
        id = entity.getId();
        ticket = ticketMapper.toResponseDTO( entity.getTicket() );

        AssignmentResponseDTO assignmentResponseDTO = new AssignmentResponseDTO( id, ticket, employee, assignmentDate );

        return assignmentResponseDTO;
    }

    @Override
    public List<AssignmentResponseDTO> toDTOList(List<AssignmentEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<AssignmentResponseDTO> list = new ArrayList<AssignmentResponseDTO>( entities.size() );
        for ( AssignmentEntity assignmentEntity : entities ) {
            list.add( toDTO( assignmentEntity ) );
        }

        return list;
    }

    @Override
    public AssignmentEntity toEntity(AssignmentCreateDTO createDTO) {
        if ( createDTO == null ) {
            return null;
        }

        AssignmentEntity assignmentEntity = new AssignmentEntity();

        assignmentEntity.setTicket( ticketMapper.createTicketEntityFromId( createDTO.getTicketId() ) );
        assignmentEntity.setEmployee( employeeMapper.createEmployeeEntityFromId( createDTO.getEmployeeId() ) );

        return assignmentEntity;
    }
}
