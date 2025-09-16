package com.leoalelui.ticketsystem.persistence.dao;

import com.leoalelui.ticketsystem.domain.dto.request.TicketRecordCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.TicketRecordResponseDTO;
import com.leoalelui.ticketsystem.persistence.entity.TicketRecordEntity;
import com.leoalelui.ticketsystem.persistence.mapper.TicketRecordMapper;
import com.leoalelui.ticketsystem.persistence.repository.TicketRecordRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class TicketRecordDAO {

    private final TicketRecordRepository ticketRecordRepository;
    private final TicketRecordMapper ticketRecordMapper;

    @Transactional(readOnly = true)
    public List<TicketRecordResponseDTO> findTicketRecordByTicketId(Long ticketId) {
        return ticketRecordMapper.toDTOList(ticketRecordRepository.findTicketRecordByTicketId(ticketId));
    }

    @Transactional
    public TicketRecordResponseDTO create(TicketRecordCreateDTO ticketRecordCreateDTO) {
        TicketRecordEntity entity = ticketRecordMapper.toEntity(ticketRecordCreateDTO);
        return ticketRecordMapper.toResponseDTO(ticketRecordRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public List<TicketRecordResponseDTO> findTicketRecordByTicketIdAndDateRange(Long ticketId,
            LocalDateTime start,
            LocalDateTime end) {
        List<TicketRecordEntity> entities
                = ticketRecordRepository.findTicketRecordByTicketIdAndChangedDateBetween(ticketId, start, end);
        return ticketRecordMapper.toDTOList(entities);
    }
}
