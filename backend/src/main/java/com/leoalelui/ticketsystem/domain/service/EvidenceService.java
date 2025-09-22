package com.leoalelui.ticketsystem.domain.service;

import com.leoalelui.ticketsystem.domain.dto.request.EvidenceCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.EvidenceResponseDTO;

import java.util.List;

public interface EvidenceService {
    EvidenceResponseDTO findById(Long id);
    List<EvidenceResponseDTO> findAllByTicketId(Long ticketId);
    EvidenceResponseDTO save (EvidenceCreateDTO evidenceCreateDTO);
    void delete(Long id);
}
