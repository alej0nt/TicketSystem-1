package com.leoalelui.ticketsystem.domain.service.impl;

import com.leoalelui.ticketsystem.domain.dto.request.NotificationCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.NotificationResponseDTO;
import com.leoalelui.ticketsystem.domain.service.NotificationService;
import com.leoalelui.ticketsystem.persistence.dao.NotificationDAO;
import com.leoalelui.ticketsystem.persistence.dao.EmployeeDAO;
import com.leoalelui.ticketsystem.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationDAO notificationDAO;
    private final EmployeeDAO employeeDAO;

    @Override
    public NotificationResponseDTO create(NotificationCreateDTO createDTO) {
        Long employeeId = createDTO.getEmployeeId();

        // Validar existencia del empleado
        validateEmployeeExists(employeeId);

        return notificationDAO.create(createDTO);
    }

    @Override
    public List<NotificationResponseDTO> getNotificationsByEmployee(Long employeeId) {
        validateEmployeeExists(employeeId);

        return notificationDAO.findByEmployeeId(employeeId);
    }

    @Override
    public NotificationResponseDTO markAsRead(Long notificationId) {
        // Validar existencia antes de modificar
        if (!notificationDAO.existsById(notificationId)) {
            throw new ResourceNotFoundException("No se ha encontrado la notificacion con id: " + notificationId);
        }

        return notificationDAO.markAsRead(notificationId);
    }

    private void validateEmployeeExists(Long employeeId) {
        if (!employeeDAO.existsById(employeeId)) {
            throw new ResourceNotFoundException("Empleado no encontrado con id: " + employeeId);
        }
    }

}
