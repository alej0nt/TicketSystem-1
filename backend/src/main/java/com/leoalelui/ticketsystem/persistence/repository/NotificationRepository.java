package com.leoalelui.ticketsystem.persistence.repository;

import com.leoalelui.ticketsystem.persistence.entity.NotificationEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author leona
 */
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long>{
    List<NotificationEntity> findByEmployeeId(Long employeeId);
}
