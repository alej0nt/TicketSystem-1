package com.leoalelui.ticketsystem.persistence.dao;

import com.leoalelui.ticketsystem.persistence.entity.TicketEntity;
import com.leoalelui.ticketsystem.persistence.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TicketDAO {
    private final TicketRepository ticketRepository;

    public TicketEntity save(TicketEntity ticket) {
        return ticketRepository.save(ticket);
    }

    public void deleteById(Long id) {
        ticketRepository.deleteById(id);
    }

    public Optional<TicketEntity> findById(Long id) {
        return ticketRepository.findById(id);
    }

    public List<TicketEntity> findAll() {
        return ticketRepository.findAll();
    }

    public List<TicketEntity> findByState(String state) {
        return ticketRepository.findByState(state);
    }

    public boolean existsById(Long id) {
        return ticketRepository.existsById(id);
    }
}
