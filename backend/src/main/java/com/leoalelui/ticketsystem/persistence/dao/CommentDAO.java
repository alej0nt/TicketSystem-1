package com.leoalelui.ticketsystem.persistence.dao;

import com.leoalelui.ticketsystem.domain.dto.response.CommentResponseDTO;
import com.leoalelui.ticketsystem.persistence.mapper.CommentMapper;
import com.leoalelui.ticketsystem.persistence.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentDAO {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public List<CommentResponseDTO> findAllByTicketId(Long ticketId) {
        return commentRepository.findAllByTicketId(ticketId)
                .stream()
                .map(commentMapper::toResponseDTO)
                .toList();
    }
}
