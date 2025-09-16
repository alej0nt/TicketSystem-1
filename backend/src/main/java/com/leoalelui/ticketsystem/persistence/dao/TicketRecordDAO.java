package com.leoalelui.ticketsystem.persistence.dao;

import com.leoalelui.ticketsystem.domain.dto.response.TicketRecordResponseDTO;
import com.leoalelui.ticketsystem.domain.dto.response.TicketResponseDTO;
import com.leoalelui.ticketsystem.persistence.entity.TicketRecordEntity;
import com.leoalelui.ticketsystem.persistence.mapper.TicketMapper;
import com.leoalelui.ticketsystem.persistence.mapper.TicketRecordMapper;
import com.leoalelui.ticketsystem.persistence.repository.CommentRepository;
import com.leoalelui.ticketsystem.persistence.repository.TicketRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TicketRecordDAO {
    private final TicketRecordRepository ticketRecordRepository;
    private final TicketRecordMapper ticketRecordMapper;

    public List<TicketRecordResponseDTO> findTicketRecordByTicketId(Long ticketId) {
        return ticketRecordRepository.findTicketRecordByTicketId(ticketId)
                .stream()
                .map(ticketRecordMapper::toResponseDTO)
                .toList();
    }
}
