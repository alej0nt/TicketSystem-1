package com.leoalelui.ticketsystem.domain.service;

import com.leoalelui.ticketsystem.domain.dto.request.TicketRecordCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.TicketRecordResponseDTO;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author leonardo Argoty
 */
public interface TicketRecordService {
    TicketRecordResponseDTO create(TicketRecordCreateDTO ticketRecordCreateDTO);
    List<TicketRecordResponseDTO> getByTicketId(Long ticketId);
    List<TicketRecordResponseDTO> getByDateRange(Long ticketId, LocalDate from, LocalDate to);
}
