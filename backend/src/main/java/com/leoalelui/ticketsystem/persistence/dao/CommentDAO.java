package com.leoalelui.ticketsystem.persistence.dao;

import com.leoalelui.ticketsystem.persistence.entity.CommentEntity;
import com.leoalelui.ticketsystem.persistence.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentDAO {
    private final CommentRepository commentRepository;

    public List<CommentEntity> findAllByTicketId(Long ticketId) {
        return commentRepository.findAllByTicketId(ticketId);
    }
}
