package com.leoalelui.ticketsystem.persistence.dao;

import com.leoalelui.ticketsystem.domain.dto.request.CommentCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.CommentResponseDTO;
import com.leoalelui.ticketsystem.persistence.entity.CommentEntity;
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

    public CommentResponseDTO save (CommentCreateDTO commentCreateDTO) {
        CommentEntity commentEntity = commentMapper.toEntity(commentCreateDTO);
        CommentEntity commentEntitySaved = commentRepository.save(commentEntity);
        return commentMapper.toResponseDTO(commentEntitySaved);
    }

    public boolean delete (Long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
