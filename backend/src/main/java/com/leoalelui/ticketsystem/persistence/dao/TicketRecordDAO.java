package com.leoalelui.ticketsystem.persistence.dao;

import com.leoalelui.ticketsystem.persistence.entity.TicketRecordEntity;
import com.leoalelui.ticketsystem.persistence.repository.TicketRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TicketRecordDAO {
    private final TicketRecordRepository ticketRecordRepository;

    public List<TicketRecordEntity> findTicketRecordByTicketId(Long ticketId) {
        return ticketRecordRepository.findTicketRecordByTicketId(ticketId);
    }
}
