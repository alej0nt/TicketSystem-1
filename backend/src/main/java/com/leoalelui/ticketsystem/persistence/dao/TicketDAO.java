package com.leoalelui.ticketsystem.persistence.dao;

import com.leoalelui.ticketsystem.domain.dto.request.TicketCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.request.TicketUpdateStateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.TicketResponseDTO;
import com.leoalelui.ticketsystem.persistence.entity.TicketEntity;
import com.leoalelui.ticketsystem.persistence.enums.State;
import com.leoalelui.ticketsystem.persistence.mapper.TicketMapper;
import com.leoalelui.ticketsystem.persistence.repository.TicketRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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

    @PersistenceContext
    private EntityManager entityManager;

    public TicketResponseDTO save(TicketCreateDTO createDTO) {
        TicketEntity entity = ticketMapper.toEntity(createDTO);
        entity.setState(State.ABIERTO);
        entity.setCreationDate(LocalDateTime.now());
        TicketEntity saved = ticketRepository.save(entity);
        entityManager.flush();
        entityManager.refresh(saved);
        return ticketMapper.toResponseDTO(saved);
    }

    public Optional<TicketResponseDTO> updateState(Long id, TicketUpdateStateDTO updateDTO) {
        return ticketRepository.findById(id)
                .map(entity -> {
                    State previousState = entity.getState();
                    State newState = updateDTO.getState();
                    entity.setState(newState);

                    if (newState == State.CERRADO) {
                        entity.setClosingDate(LocalDateTime.now());
                    } else if (previousState == State.CERRADO && newState == State.RESUELTO) {
                        entity.setClosingDate(null);
                    }

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

    public List<TicketResponseDTO> findAll() {
        return ticketRepository.findAll()
                .stream()
                .map(ticketMapper::toResponseDTO)
                .toList();
    }

    public List<TicketResponseDTO> findByState(State state) {
        return ticketRepository.findByState(state)
                .stream()
                .map(ticketMapper::toResponseDTO)
                .toList();
    }

    public boolean existsById(Long id) {
        return ticketRepository.existsById(id);
    }
}
