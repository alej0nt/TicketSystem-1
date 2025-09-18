package com.leoalelui.ticketsystem.persistence.dao;

import com.leoalelui.ticketsystem.domain.dto.request.NotificationCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.NotificationResponseDTO;
import com.leoalelui.ticketsystem.persistence.entity.NotificationEntity;
import com.leoalelui.ticketsystem.persistence.mapper.NotificationMapper;
import com.leoalelui.ticketsystem.persistence.repository.NotificationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author leona
 */
@RequiredArgsConstructor
@Repository
public class NotificationDAO {
    private final NotificationMapper notificationMapper;
    private final NotificationRepository notificationRepository;
    
    @Transactional
    public NotificationResponseDTO create(NotificationCreateDTO NotificationCreateDTO) {
        NotificationEntity entity = notificationMapper.toEntity(NotificationCreateDTO);
        return notificationMapper.toResponseDTO(notificationRepository.save(entity));
    }
    
    @Transactional(readOnly = true)
    public List<NotificationResponseDTO> findByEmployeeId(Long employeeId) {
        return notificationMapper.toDTOList(notificationRepository.findByEmployeeId(employeeId));
    }
    
    public boolean existsById(Long id) {
        return notificationRepository.existsById(id);
    }

    @Transactional
    public NotificationResponseDTO markAsRead(Long id) {
        NotificationEntity notification = notificationRepository.findById(id).get();

        notification.setRead(true);
        NotificationEntity updated = notificationRepository.save(notification);
        return notificationMapper.toResponseDTO(updated);
    }
}
