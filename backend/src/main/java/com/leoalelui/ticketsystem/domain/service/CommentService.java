package com.leoalelui.ticketsystem.domain.service;

import com.leoalelui.ticketsystem.domain.dto.request.CommentCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.CommentResponseDTO;

import java.util.List;

public interface CommentService {
    CommentResponseDTO save (CommentCreateDTO commentCreateDTO);
    void delete (long id);
    List<CommentResponseDTO> findAllByTicketId(long id);

}
