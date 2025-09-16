package com.leoalelui.ticketsystem.persistence.repository;

import com.leoalelui.ticketsystem.persistence.entity.TicketRecordEntity;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRecordRepository extends JpaRepository<TicketRecordEntity, Long> {
    List<TicketRecordEntity> findTicketRecordByTicketId(Long ticketId);
    List<TicketRecordEntity> findTicketRecordByTicketIdAndChangedDateBetween(
            Long ticketId,
            LocalDateTime start,
            LocalDateTime end
    );
}
