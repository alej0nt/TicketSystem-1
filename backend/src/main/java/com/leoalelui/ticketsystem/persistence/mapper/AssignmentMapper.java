package com.leoalelui.ticketsystem.persistence.mapper;

import com.leoalelui.ticketsystem.domain.dto.request.AssignmentCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.AssignmentResponseDTO;
import com.leoalelui.ticketsystem.persistence.entity.AssignmentEntity;
import com.leoalelui.ticketsystem.persistence.entity.EmployeeEntity;
import com.leoalelui.ticketsystem.persistence.entity.TicketEntity;
import org.mapstruct.*;

import java.util.List;

/**
 * @author Leonardo Argoty
 * Mapper para Assignment 
 */
@Mapper(
        componentModel = "spring",
        uses = {TicketMapper.class, EmployeeMapper.class},
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface AssignmentMapper {

    /**
     * Convierte entidad a dto de respuesta para lectura
     */
    AssignmentResponseDTO toDTO(AssignmentEntity entity);

    /**
     * Convierte lista de entidades a lista de dtos de respuesta para lectura
     */
    List<AssignmentResponseDTO> toDTOList(List<AssignmentEntity> entities);

    /**
     * Convierte dto a entidad para cuando se crea
     * Se ignora id y assignmentDate
     * Se convierte ticketId y employeeId a sus entidades referenciadas usando métodos @Named
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assignmentDate", ignore = true)
    @Mapping(target = "ticket", source = "ticketId", qualifiedByName = "createTicketEntityFromId")
    @Mapping(target = "employee", source = "employeeId", qualifiedByName = "createEmployeeEntityFromId")
    AssignmentEntity toEntity(AssignmentCreateDTO createDTO);

    /**
     * Crea TicketEntity con solo el ID (referencia JPA)
     */

}
