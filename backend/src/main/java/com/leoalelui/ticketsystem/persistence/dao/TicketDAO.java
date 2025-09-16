package com.leoalelui.ticketsystem.persistence.dao;

import com.leoalelui.ticketsystem.domain.dto.request.TicketCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.request.TicketUpdateStateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.TicketResponseDTO;
import com.leoalelui.ticketsystem.persistence.entity.TicketEntity;
import com.leoalelui.ticketsystem.persistence.mapper.TicketMapper;
import com.leoalelui.ticketsystem.persistence.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TicketDAO {

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    public TicketResponseDTO save(TicketCreateDTO createDTO) {
        TicketEntity entity = ticketMapper.toEntity(createDTO);
        entity.setState("ABIERTO");
        entity.setCreationDate(LocalDateTime.now());
        TicketEntity saved = ticketRepository.save(entity);
        return ticketMapper.toResponseDTO(saved);
    }

    public Optional<TicketResponseDTO> updateState(Long id, TicketUpdateStateDTO updateDTO) {
        return ticketRepository.findById(id)
                .map(entity -> {
                    entity.setState(updateDTO.getState());
                    TicketEntity updated = ticketRepository.save(entity);
                    return ticketMapper.toResponseDTO(updated);
                });
    }


    public void deleteById(Long id) {
        ticketRepository.deleteById(id);
    }

    public Optional<TicketResponseDTO> findById(Long id) {
        return ticketRepository.findById(id)
                .map(ticketMapper::toResponseDTO);
    }

    public Optional<TicketEntity> findEntityById(Long id) {
        return ticketRepository.findById(id);
    }

    public List<TicketResponseDTO> findAll() {
        return ticketRepository.findAll()
                .stream()
                .map(ticketMapper::toResponseDTO)
                .toList();
    }

    public List<TicketResponseDTO> findByState(String state) {
        return ticketRepository.findByState(state)
                .stream()
                .map(ticketMapper::toResponseDTO)
                .toList();
    }

    public boolean existsById(Long id) {
        return ticketRepository.existsById(id);
    }
}
