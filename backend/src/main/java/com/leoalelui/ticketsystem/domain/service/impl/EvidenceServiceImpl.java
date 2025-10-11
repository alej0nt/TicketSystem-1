package com.leoalelui.ticketsystem.domain.service.impl;

import com.leoalelui.ticketsystem.domain.dto.request.EvidenceCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.EvidenceResponseDTO;
import com.leoalelui.ticketsystem.domain.dto.response.TicketResponseDTO;
import com.leoalelui.ticketsystem.domain.exception.ResourceNotFoundException;
import com.leoalelui.ticketsystem.domain.service.EvidenceService;
import com.leoalelui.ticketsystem.persistence.dao.EvidenceDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class EvidenceServiceImpl implements EvidenceService {
    private final EvidenceDAO evidenceDAO;
    private final TicketServiceImpl ticketService;

    @Override
    public EvidenceResponseDTO findById(Long id) {
        return evidenceDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado una evidencia con el ID: " + id));
    }

    @Override
    public List<EvidenceResponseDTO> findAllByTicketId(Long ticketId) {
        TicketResponseDTO ticket = ticketService.getTicketById(ticketId);
        return evidenceDAO.findAllByTicketId(ticketId);
    }

    @Override
    public EvidenceResponseDTO save(EvidenceCreateDTO evidenceCreateDTO) {
        TicketResponseDTO ticket = ticketService.getTicketById(evidenceCreateDTO.getTicketId());
        return evidenceDAO.save(evidenceCreateDTO);
    }

    @Override
    public void delete(Long id) {
        findById(id);
        evidenceDAO.delete(id);
    }
}
