package com.leoalelui.ticketsystem.persistence.mapper;

import com.leoalelui.ticketsystem.domain.dto.request.NotificationCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.NotificationResponseDTO;
import com.leoalelui.ticketsystem.persistence.entity.NotificationEntity;
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
public class NotificationMapperImpl implements NotificationMapper {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public NotificationEntity toEntity(NotificationCreateDTO notificationCreateDTO) {
        if ( notificationCreateDTO == null ) {
            return null;
        }

        NotificationEntity notificationEntity = new NotificationEntity();

        notificationEntity.setEmployee( employeeMapper.createEmployeeEntityFromId( notificationCreateDTO.getEmployeeId() ) );
        notificationEntity.setMessage( notificationCreateDTO.getMessage() );

        return notificationEntity;
    }

    @Override
    public NotificationResponseDTO toResponseDTO(NotificationEntity commentEntity) {
        if ( commentEntity == null ) {
            return null;
        }

        LocalDateTime creationDate = null;
        Long id = null;
        String message = null;

        creationDate = commentEntity.getCreationDate();
        id = commentEntity.getId();
        message = commentEntity.getMessage();

        boolean isRead = false;

        NotificationResponseDTO notificationResponseDTO = new NotificationResponseDTO( id, message, creationDate, isRead );

        notificationResponseDTO.setRead( commentEntity.isRead() );

        return notificationResponseDTO;
    }

    @Override
    public List<NotificationResponseDTO> toDTOList(List<NotificationEntity> commentEntity) {
        if ( commentEntity == null ) {
            return null;
        }

        List<NotificationResponseDTO> list = new ArrayList<NotificationResponseDTO>( commentEntity.size() );
        for ( NotificationEntity notificationEntity : commentEntity ) {
            list.add( toResponseDTO( notificationEntity ) );
        }

        return list;
    }
}
