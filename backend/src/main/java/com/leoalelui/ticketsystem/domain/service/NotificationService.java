package com.leoalelui.ticketsystem.domain.service;

import com.leoalelui.ticketsystem.domain.dto.response.NotificationResponseDTO;
import java.util.List;

/**
 *
 * @author leona
 */
public interface NotificationService {
    List<NotificationResponseDTO> getNotificationsByEmployee(Long employeeId);
    NotificationResponseDTO markAsRead(Long notificationId);
}
