package com.leoalelui.ticketsystem.persistence.repository;

import com.leoalelui.ticketsystem.persistence.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByTicketId(Long ticketId);
}
