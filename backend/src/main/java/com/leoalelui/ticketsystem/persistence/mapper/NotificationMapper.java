package com.leoalelui.ticketsystem.persistence.mapper;

import com.leoalelui.ticketsystem.domain.dto.request.NotificationCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.NotificationResponseDTO;
import com.leoalelui.ticketsystem.persistence.entity.NotificationEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 * @author leona
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface NotificationMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "employee", source = "employeeId", qualifiedByName = "createEmployeeEntityFromId")
    @Mapping(target = "read", ignore = true)
    NotificationEntity toEntity(NotificationCreateDTO notificationCreateDTO);

    @Mapping(target = "isRead", source = "read")
    NotificationResponseDTO toResponseDTO(NotificationEntity commentEntity);
    List<NotificationResponseDTO> toDTOList(List<NotificationEntity> commentEntity);
}
